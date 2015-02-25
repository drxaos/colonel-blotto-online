package blotto.mail.app

import blotto.mail.system.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MailHelper {

    @Autowired
    MailService mailService

    void send(String to, String view, Map model) {
        mailService.send(to, view, model)
    }

    void send(String from, String to, String view, Map model) {
        mailService.send(from, to, view, model)
    }

    void sendTest(String to, String msg) {
        mailService.send(to, "/mail/test", [test: msg])
    }

}
