package blotto.test.webTests

import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.pages.LoginPage
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

        then: "Player is at the login page"
        at LoginPage
    }
}