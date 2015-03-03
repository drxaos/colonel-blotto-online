/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blotto.mail.system

import com.sun.mail.smtp.SMTPMessage
import org.joda.time.DateTime
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.InputStreamSource
import org.springframework.mail.MailMessage
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.util.Assert

import javax.mail.Message
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeUtility
import java.util.concurrent.ExecutorService

/**
 * Provides a DSL style interface to mail message sending/generation.
 *
 * If the builder is constructed without a MailMessageContentRenderer, it is incapable
 * of rendering GSP views into message content.
 */
class MailMessageBuilder {

    private static final Logger log = LoggerFactory.getLogger(MailMessageBuilder.class)

    final MailSender mailSender
    final MailMessageContentRenderer mailMessageContentRenderer
    final MailLogService mailLogService

    final String defaultFrom
    final String defaultTo

    private MailMessage message
    private MimeMessageHelper helper
    private Locale locale

    private String textContent
    private String htmlContent
    private String envelopeFrom

    String mailHtml
    String mailView

    private String from
    private String to
    private Date date
    private String subject

    private int multipart = MimeMessageHelper.MULTIPART_MODE_NO
    private boolean async = false

    private List<Inline> inlines = []

    private static class Inline {
        String id
        String contentType
        InputStreamSource toAdd
    }

    MailMessageBuilder(MailLogService mailLogService, MailSender mailSender, MailMessageContentRenderer mailMessageContentRenderer, Map params) {
        this.mailSender = mailSender
        this.mailMessageContentRenderer = mailMessageContentRenderer
        this.mailLogService = mailLogService

        this.defaultFrom = this.defaultTo = params.defaultFrom ?: "root@localhost"
    }

    private MailMessage getMessage() {
        if (!message) {
            if (mimeCapable) {
                helper = new MimeMessageHelper(mailSender.createMimeMessage(), multipart)
                message = new MimeMailMessage(helper)
            } else {
                message = new SimpleMailMessage()
            }

            if (defaultFrom) {
                message.from = from = defaultFrom
            }

            if (defaultTo) {
                message.setTo(to = defaultTo)
            }
        }

        message
    }

    MailMessage sendMessage(ExecutorService executorService) {
        MailMessage message = finishMessage()

        if (log.traceEnabled) {
            log.trace("Sending mail ${getDescription(message)}} ...")
        }

        def sendingMsg = message instanceof MimeMailMessage ? message.mimeMessage : message
        if (envelopeFrom) {
            if (!mimeCapable) {
                throw new MailException("You must use a JavaMailSender to set the envelopeFrom.")
            }

            sendingMsg = new SMTPMessage(sendingMsg)
            sendingMsg.envelopeFrom = envelopeFrom
        }

        if (async) {
            executorService.execute({
                try {
                    mailSender.send(sendingMsg)
                } catch (Throwable t) {
                    if (log.errorEnabled) log.error("Failed to send email", t)
                }
            } as Runnable)
        } else {
            mailSender.send(sendingMsg)
        }

        if (log.traceEnabled) {
            log.trace("Sent mail ${getDescription(message)}} ...")
        }

        message
    }

    void multipart(boolean multipart) {
        this.multipart = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED
    }

    void async(boolean async) {
        this.async = async
    }

    void multipart(int multipartMode) {
        this.multipart = multipartMode
    }

    void headers(Map hdrs) {
        Assert.notEmpty(hdrs, "headers cannot be null")

        // The message must be of type MimeMailMessage to add headers.
        if (!mimeCapable) {
            throw new MailException("You must use a JavaMailSender to customise the headers.")
        }

        MailMessage msg = getMessage()
        if (msg instanceof MimeMailMessage) {
            MimeMessage mimeMessage = ((MimeMailMessage) msg).mimeMessageHelper.mimeMessage
            hdrs.each { name, value ->
                String nameString = name?.toString()
                String valueString = value?.toString()

                Assert.hasText(nameString, "header names cannot be null or empty")
                Assert.hasText(valueString, "header value for '$nameString' cannot be null")

                mimeMessage.setHeader(nameString, valueString)
            }
        } else {
            throw new MailException("mail message builder is not mime capable so headers cannot be set")
        }
    }

    void to(Object[] args) {
        Assert.notEmpty(args, "to cannot be null or empty")
        Assert.noNullElements(args, "to cannot contain null elements")

        getMessage().setTo(toDestinationAddresses(args))

        this.to = toDestinationAddresses(args)[0]
    }

    void to(List args) {
        Assert.notEmpty(args, "to cannot be null or empty")
        Assert.noNullElements(args.toArray(), "to cannot contain null elements")

        to(*args)
    }

    void bcc(Object[] args) {
        Assert.notEmpty(args, "bcc cannot be null or empty")
        Assert.noNullElements(args, "bcc cannot contain null elements")

        getMessage().setBcc(toDestinationAddresses(args))
    }

    void bcc(List args) {
        Assert.notEmpty(args, "bcc cannot be null or empty")
        Assert.noNullElements(args.toArray(), "bcc cannot contain null elements")

        bcc(*args)
    }

    void cc(Object[] args) {
        Assert.notEmpty(args, "cc cannot be null or empty")
        Assert.noNullElements(args, "cc cannot contain null elements")

        getMessage().setCc(toDestinationAddresses(args))
    }

    void cc(List args) {
        Assert.notEmpty(args, "cc cannot be null or empty")
        Assert.noNullElements(args.toArray(), "cc cannot contain null elements")

        cc(*args)
    }

    void replyTo(CharSequence replyTo) {
        Assert.hasText(replyTo, "replyTo cannot be null or 0 length")

        getMessage().replyTo = replyTo.toString()
    }

    void from(CharSequence from) {
        Assert.hasText(from, "from cannot be null or 0 length")

        getMessage().from = from.toString()
        this.from = from
    }

    void envelopeFrom(CharSequence envFrom) {
        Assert.hasText(envFrom, "envelope from cannot be null or 0 length")

        envelopeFrom = envFrom.toString()
    }

    void title(CharSequence title) {
        Assert.notNull(title, "title cannot be null")

        subject(title)
    }

    void subject(CharSequence title) {
        Assert.notNull(title, "subject cannot be null")

        getMessage().subject = title.toString()
        this.subject = title.toString()
    }

    void body(CharSequence body) {
        Assert.notNull(body, "body cannot be null")

        text(body)
    }

    void body(Map params) {
        Assert.notEmpty(params, "body cannot be null or empty")

        def render = doRender(params)
        subject(render?.subject?.toString())
        html(render?.out?.toString())
    }

    protected def doRender(Map params) {
        if (mailMessageContentRenderer == null) {
            throw new MailException("mail message builder was constructed without a message content render so cannot render views")
        }

        if (!params.view) {
            throw new MailException("no view specified")
        }

        return mailMessageContentRenderer.render(new StringWriter(), params.view, params.model, locale, params.plugin)
    }

    void text(Map params) {
        Assert.notEmpty(params, "text cannot be null or empty")

        def render = doRender(params)
        subject(render?.subject?.toString())
        text(render?.out?.toString())
    }

    void text(CharSequence text) {
        Assert.notNull(text, "text cannot be null")

        textContent = text.toString()
    }

    void html(Map params) {
        Assert.notEmpty(params, "html cannot be null or empty")

        def render = doRender(params)
        subject(render?.subject?.toString())
        html(render?.out?.toString())
        this.mailView = params.view
    }

    void html(CharSequence text) {
        Assert.notNull(text, "html cannot be null")

        if (mimeCapable) {
            htmlContent = text.toString()
        } else {
            throw new MailException("mail sender is not mime capable, try configuring a JavaMailSender")
        }

        this.mailHtml = text
    }

    void locale(String localeStr) {
        Assert.hasText(localeStr, "locale cannot be null or empty")

        locale(new Locale(*localeStr.split('_', 3)))
    }

    void locale(Locale locale) {
        Assert.notNull(locale, "locale cannot be null")

        this.locale = locale
    }

    /**
     * @deprecated use attach(String, String, byte[])
     */
    void attachBytes(String fileName, String contentType, byte[] bytes) {
        attach(fileName, contentType, bytes)
    }

    void attach(String fileName, String contentType, byte[] bytes) {
        attach(fileName, contentType, new ByteArrayResource(bytes))
    }

    void attach(File file) {
        attach(file.name, file)
    }

    void attach(String fileName, File file) {
        if (!mimeCapable) {
            throw new MailException("Message is not an instance of org.springframework.mail.javamail.MimeMessage, cannot attach bytes!")
        }

        attach(fileName, helper.fileTypeMap.getContentType(file), file)
    }

    void attach(String fileName, String contentType, File file) {
        if (!file.exists()) {
            throw new FileNotFoundException("cannot use $file as an attachment as it does not exist")
        }

        attach(fileName, contentType, new FileSystemResource(file))
    }

    void attach(String fileName, String contentType, InputStreamSource source) {
        doAdd(fileName, contentType, source, true)
    }

    void inline(String contentId, String contentType, byte[] bytes) {
        inline(contentId, contentType, new ByteArrayResource(bytes))
    }

    void inline(File file) {
        inline(file.name, file)
    }

    void inline(String fileName, File file) {
        if (!mimeCapable) {
            throw new MailException("Message is not an instance of org.springframework.mail.javamail.MimeMessage, cannot attach bytes!")
        }

        inline(fileName, helper.fileTypeMap.getContentType(file), file)
    }

    void inline(String contentId, String contentType, File file) {
        if (!file.exists()) {
            throw new FileNotFoundException("cannot use $file as an attachment as it does not exist")
        }

        inline(contentId, contentType, new FileSystemResource(file))
    }

    void inline(String contentId, String contentType, InputStreamSource source) {
        inlines << new Inline(id: contentId, contentType: contentType, toAdd: source)
    }

    protected doAdd(String id, String contentType, InputStreamSource toAdd, boolean isAttachment) {
        if (!mimeCapable) {
            throw new MailException("Message is not an instance of org.springframework.mail.javamail.MimeMessage, cannot attach bytes!")
        }

        assert multipart, "message is not marked as 'multipart'; use 'multipart true' as the first line in your builder DSL"

        getMessage() //ensure that helper is initialized
        if (isAttachment) {
            helper.addAttachment(MimeUtility.encodeWord(id), toAdd, contentType)
        } else {
            helper.addInline(MimeUtility.encodeWord(id), toAdd, contentType)
        }
    }

    boolean isMimeCapable() {
        mailSender instanceof JavaMailSender
    }

    protected String[] toDestinationAddresses(addresses) {
        addresses.collect { it?.toString() } as String[]
    }

    protected getDescription(SimpleMailMessage message) {
        "[${message.subject}] from [${message.from}] to ${message.to}"
    }

    protected getDescription(Message message) {
        "[${message.subject}] from [${message.from}] to ${message.getRecipients(Message.RecipientType.TO)*.toString()}"
    }

    protected getDescription(MimeMailMessage message) {
        getDescription(message.mimeMessage)
    }

    MailMessage finishMessage() {
        MailMessage message = getMessage()

        if (htmlContent) {
            if (textContent) {
                helper.setText(textContent, htmlContent)
            } else {
                helper.setText(htmlContent, true)
            }
        } else {
            if (!textContent) {
                throw new MailException("message has no content, use text(), html() or body() methods to set content")
            }

            message.text = textContent
        }

        inlines.each {
            doAdd(it.id, it.contentType, it.toAdd, false)
        }

        message.sentDate = DateTime.now().toDate()

        this.date = DateTime.now().toDate()
        message.sentDate = this.date
        saveToLog()
        return message
    }


    void plainAndHtml(Map params) {
        html(params)
        String str = this.mailHtml
        str = textify(str)
        text(str)
    }

    void plainAndHtml(CharSequence params) {
        html(params)
        String str = this.mailHtml
        str = textify(str)
        text(str)
    }

    private String textify(String str) {
        //вырезать со всем содержимым: комментари <!-- -->, теги style, script, noscript, head, meta, link, object
        str = str.replaceAll("<\\s*!--.*--\\s*>", "")
                .replaceAll("(?s)<head>.*</head>", "")
                .replaceAll("(?s)<\\s*style.*/\\s*style\\s*>", "")
                .replaceAll("<(?s)\\s*(no)?script.*/\\s*(no)?script\\s*>", "")
                .replaceAll("(?s)<\\s*meta.*/\\s*meta\\s*>", "")
                .replaceAll("(?s)<\\s*link.*/\\s*link\\s*>", "")
                .replaceAll("(?s)<\\s*object.*/\\s*object\\s*>", "")
        //удаляем лишние вайтспейсы
        str = str.replaceAll(">\\s{2,}", ">")
                .replaceAll("\\s{2,}<", "<")
                .replaceAll("\\s{2,}", " ")
        //делать переносы после тегов: br, h1, h2, h3, h4, h5, h6, ul, ol, li, dl, dt, dd, pre, div, p
        str = str.replaceAll("<\\s*br\\s*/\\s*>", "\r\n")
                .replaceAll("<\\s*br\\s*>", "\r\n")
                .replaceAll("<\\s*/\\s*h[123456]\\s*>", "\r\n")
                .replaceAll("ul\\s*>", "ul>\r\n")
                .replaceAll("ol\\s*>", "ol>\r\n")
                .replaceAll("/\\s*li\\s*>", "/li>\r\n")
                .replaceAll("dl\\s*>", "dl>\r\n")
                .replaceAll("/\\s*dt\\s*>", "/dt>\r\n")
                .replaceAll("/\\s*dd\\s*>", "/dd>\r\n")
                .replaceAll("<\\s*pre\\s*>", "<pre>\r\n")
                .replaceAll("<\\s*/\\s*pre\\s*>", "</pre>\r\n")
                .replaceAll("<\\s*div\\s*>", "\r\n")
                .replaceAll("<\\s*/\\s*div\\s*>", "\r\n")
                .replaceAll("<\\s*p\\s*>", "<p>\r\n")
                .replaceAll("<\\s*/\\s*p\\s*>", "</p>\r\n")
        //нужно выносить содержимое атрибута href для тегов «а»
        str = str.replaceAll("(?s)<\\s*a\\s+href\\s*=\\w*[\"\']([.[^>]]*)[\"\']\\s*>([.[^>]]*)<\\s*/\\s*a\\s*>", "\$2 (\$1)")
        //убираем все теги
        str = str.replaceAll("<\\s*\\W?[.[^>]]*>", "").trim()
        str
    }

    void saveToLog() {
        try {
            mailLogService.save(date, to, subject, mailHtml, from, mailView)
        } catch (Exception e) {
            log.error("error while saving mail log", e)
        }
    }
}
