package blotto.test.serviceTests

import blotto.domain.Player
import blotto.domain.User
import blotto.errors.player.EmailAlreadyExists
import blotto.errors.player.UsernameAlreadyExists
import blotto.service.app.PlayerService
import blotto.test.AbstractSpringTest
import blotto.utils.mail.SmtpStubServer
import junit.framework.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

public class PlayerService_signupPlayer_Test extends AbstractSpringTest {

    @Autowired
    SmtpStubServer smtpStubServer

    @Autowired
    PlayerService playerService

    @Test
    public void 'signing up player'() {

        given: "there is one player already"
        playerService.createPlayer("user1", "paswd", "asd@asd.ru", "Test User")

        when: "user uses existing username"
        smtpStubServer.reset()
        playerService.signUpPlayer("user1", "paswd", "asd@asd.ru", "Test User")

        then: "registration fails"
        thrown(UsernameAlreadyExists)
        assert smtpStubServer.allMessages.size() == 0

        when: "user uses existing email"
        smtpStubServer.reset()
        playerService.signUpPlayer("user2", "paswd", "asd@asd.ru", "Another Test User")

        then: "registration fails"
        thrown(EmailAlreadyExists)
        assert smtpStubServer.allMessages.size() == 0

        when: "user signs up correctly"
        smtpStubServer.reset()
        playerService.signUpPlayer("user2", "paswd", "asd2@asd.ru", "Another Test User")

        then: "there is a new user"
        def user = User.findByUsername("user2")
        Assert.assertNotNull("user", user)
        Assert.assertEquals("user name", "Another Test User", user.fullName)
        Assert.assertEquals("user email", "asd2@asd.ru", user.email)
        def player = Player.findByUser(user)
        Assert.assertNotNull("player", player)

        and: "there is a sign up mail"
        assert smtpStubServer.allMessages.size() == 1
        def mail = smtpStubServer.allMessages.first()
        assert mail.from == "admin@blotto.v-pp.ru"
        assert mail.to == "asd2@asd.ru"
        assert mail.subject == "Вы зарегистрированы на blotto.v-pp.ru"
        assert mail.plainText.contains("Добро пожаловать, Another Test User")
    }

}