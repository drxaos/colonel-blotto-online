package blotto.utils.mail

import org.subethamail.wiser.WiserMessage

import javax.mail.Header
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.internet.MimeUtility

class MailUtil {
    private static MailMessageContent getContentFromMessage(Message mes) {
        def content = mes.getContent()
        StringBuilder html = new StringBuilder()
        StringBuilder plainText = new StringBuilder()
        if (content instanceof Multipart) {
            MailMessageContent multipartContent = getContentFromMultipart(content)
            html.append(multipartContent.html)
            plainText.append(multipartContent.plainText)
        } else if (content instanceof String) {
            plainText.append(content)
        } else {
            throw new UnsupportedOperationException("can't get text of the message")
        }
        return new MailMessageContent(html: html.toString(), plainText: plainText.toString())
    }

    private static MailMessageContent getContentFromMultipart(Multipart content) {
        StringBuilder html = new StringBuilder()
        StringBuilder plainText = new StringBuilder()
        def partsCount = ((Multipart) content).getCount()
        for (int i = 0; i < partsCount; i++) {
            def part = content.getBodyPart(i)
            if (part.content instanceof Multipart) {
                MailMessageContent multipartContent = getContentFromMultipart(part.content)
                html.append(multipartContent.html)
                plainText.append(multipartContent.plainText)
            } else if (part.isMimeType("text/html")) {
                html.append part.content
            } else if (part.isMimeType("text/plain")) {
                plainText.append part.content
            } else {
                throw new UnsupportedOperationException("can't get text of the message part")
            }
        }
        return new MailMessageContent(html: html.toString(), plainText: plainText.toString())
    }

    private static Map<String, List<String>> getHeaders(Message msg) {
        Map<String, List<String>> map = new HashMap<String, List<String>>()
        Enumeration headers = msg.allHeaders
        while (headers.hasMoreElements()) {
            Header h = (Header) headers.nextElement()
            def values = map[h.name]
            if (!values) {
                values = new ArrayList<String>()
                map[h.name] = values
            }
            values.add(MimeUtility.decodeText(h.getValue()))
        }
        return map
    }

    public static MailMessageContent decodeMessage(WiserMessage msg) {
        def mm = msg.mimeMessage
        def content = getContentFromMessage(mm)
        content.headers = getHeaders(mm)
        content.from = msg.envelopeSender
        content.to = msg.envelopeReceiver
        content.subject = content.headers.get("Subject")?.find { true }
        content.messageData = msg.data
        return content
    }
}
