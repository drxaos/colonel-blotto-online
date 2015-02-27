package blotto.test.helpers

import blotto.service.app.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlayerHelper {

    @Autowired
    PlayerService playerService

    void createPlayer(String username = "user1", String password = "passwd") {
        playerService.createPlayer(username, password, "asd@asd.ru", "Test User")
    }
}
