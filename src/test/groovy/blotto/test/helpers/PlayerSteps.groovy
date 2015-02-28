package blotto.test.helpers

import blotto.service.app.PlayerService
import blotto.test.web.pages.LoginPage
import blotto.test.web.pages.StrategyPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PlayerSteps extends AbstractSteps {

    @Autowired
    PlayerService playerService

    String username
    String password

    void createPlayer(String username = "user1", String password = "passwd") {
        playerService.createPlayer(username, password, "asd@asd.ru", "Test User")
        this.username = username
        this.password = password
    }

    void login(String username = null, String password = null) {
        to LoginPage
        usernameField = username ?: this.username
        passwordField = password ?: this.password
        submitButton.click()
        at StrategyPage
    }
}
