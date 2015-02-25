package blotto.taglib

import blotto.service.app.PlayerService
import grails.gsp.TagLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TagLib
@Component
class MailTagLib {
    static namespace = 'mail'

    @Autowired
    PlayerService playerService

    def subject = { attrs, body ->
        Binding binding = this.pageScope
        while (!binding.isRoot() && binding.getParent() != null) {
            binding = binding.getParent()
        }
        binding.setVariable("MAIL_SUBJECT", body())
    }
}
