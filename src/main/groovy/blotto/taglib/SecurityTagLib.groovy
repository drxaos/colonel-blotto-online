package blotto.taglib

import blotto.service.app.PlayerService
import grails.gsp.TagLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@TagLib
@Component
class SecurityTagLib {
    static namespace = 'sec'

    @Autowired
    PlayerService playerService

    def ifLoggedIn = { attrs, body ->
        if (playerService.currentLoggedInUser) {
            out << body()
        }
    }

    def loggedInUsername = { attrs, body ->
        out << playerService.currentLoggedInUser.fullName
    }
}
