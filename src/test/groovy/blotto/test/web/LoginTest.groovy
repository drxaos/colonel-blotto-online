package blotto.test.web

import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerHelper
import blotto.test.web.pages.LoginPage
import blotto.test.web.pages.StrategyPage
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

public class LoginTest extends AbstractSpringTest {

    @Autowired
    PlayerHelper playerHelper

    @Test
    public void 'Successful login'() {
        given: "Player exists"
        playerHelper.createPlayer()
        and: "I'm at login page"
        to LoginPage

        when: "I login as a valid user"
        usernameField = "user1"
        passwordField = "passwd"
        submitButton.click()

        then: "I'm at the main page"
        at StrategyPage
    }

    @Test
    def "Invalid user login"() {
        given: "Player exists"
        playerHelper.createPlayer()
        and: "I'm at the login form"
        to LoginPage

        when: "I login as a invalid user"
        usernameField = "qwerty"
        passwordField = "passwd"
        submitButton.click()

        then: "I'm at the login page with error"
        at LoginPage
        assert alertText == "Не удалось войти"
    }

    @Test
    def "Invalid password login"() {
        given: "Player exists"
        playerHelper.createPlayer()
        and: "I'm at the login form"
        to LoginPage

        when: "I login as a invalid user"
        usernameField = "user1"
        passwordField = "incorrect"
        submitButton.click()

        then: "I'm at the login page with error"
        at LoginPage
        assert alertText == "Не удалось войти"
    }
}