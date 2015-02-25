package blotto.taglib

import blotto.service.app.PlayerService
import grails.gsp.TagLib
import org.codehaus.groovy.grails.web.pages.GroovyPageBinding
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TagLib
@Component
class MailTagLib {
    static namespace = 'mail'

    @Autowired
    PlayerService playerService

    def subject = { attrs, body ->
        GroovyPageBinding binding = this.pageScope
        binding.setVariable("subject", body())
    }
}
