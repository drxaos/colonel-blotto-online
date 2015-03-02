package blotto.test.webTests

import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.pages.LoginPage
import blotto.test.pages.StrategyPage
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

public class LoginTest extends AbstractSpringTest {

    @Autowired
    PlayerSteps playerSteps

    @Test
    public void 'Successful login'() {
        given: "Player exists"
        playerSteps.createPlayer()
        and: "Player is at login page"
        to LoginPage

        when: "Player logins as a valid user"
        usernameField = "user1"
        passwordField = "passwd"
        submitButton.click()

        then: "Player is at the main page"
        at StrategyPage
    }

    @Test
    def "Invalid user login"() {
        given: "Player exists"
        playerSteps.createPlayer()
        and: "Player is at the login page"
        to LoginPage

        when: "Player logins as invalid user"
        usernameField = "qwerty"
        passwordField = "passwd"
        submitButton.click()

        then: "Player is at the login page with error alert"
        at LoginPage
        assert alertText == "Не удалось войти"
    }

    @Test
    def "Invalid password login"() {
        given: "Player exists"
        playerSteps.createPlayer()
        and: "Player is at the login page"
        to LoginPage

        when: "Player logins with invalid password"
        usernameField = "user1"
        passwordField = "incorrect"
        submitButton.click()

        then: "Player is at the login page with error alert"
        at LoginPage
        assert alertText == "Не удалось войти"
    }
}