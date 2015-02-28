package blotto.test.web

import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.web.pages.LoginPage
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

public class LogoutTest extends AbstractSpringTest {

    @Autowired
    PlayerSteps playerSteps

    @Test
    public void 'Successful logout'() {
        given: "Player exists"
        playerSteps.createPlayer()
        and: "Player is logged in"
        playerSteps.login()

        when: "Player logs out"
        logout.click()

        then: "I'm at the login page"
        at LoginPage
    }

}