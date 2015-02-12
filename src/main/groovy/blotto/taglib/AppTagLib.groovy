package blotto.taglib

import blotto.service.app.PlayerService
import grails.artefact.Artefact
import grails.gsp.TagLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TagLib
@Component
class AppTagLib {
    static namespace = 'app'

    @Autowired
    PlayerService playerService


}
