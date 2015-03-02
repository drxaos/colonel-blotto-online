package blotto.test.helpers

import blotto.domain.Player
import blotto.service.app.PlayerService
import blotto.test.pages.LoginPage
import blotto.test.pages.StrategyPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlayerSteps extends AbstractSteps {

    @Autowired
    PlayerService playerService

    Player createPlayer(String username = "user1", String password = "passwd") {
        def player = playerService.createPlayer(username, password, username + "@test.ru", "Test User")
        return player
    }

    void login(String username = "user1", String password = "passwd") {
        to LoginPage
        usernameField = username
        passwordField = password
        submitButton.click()
        at StrategyPage
    }
}
