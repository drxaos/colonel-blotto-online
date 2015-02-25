package blotto.mail

import blotto.errors.ServiceException
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
class MailLogService {

    @Transactional
    public save(Date date, String toEmail, String subject, String text, String sender) {
        MailLog ml = new MailLog(
                date: date,
                toEmail: toEmail,
                subject: subject,
                text: text,
                sender: sender
        )

        if (!ml.validate() || !ml.save(flush: true)) {
            throw new ServiceException("can't create mail log: ${ml}")
        }
    }

}
