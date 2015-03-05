package blotto.test.serviceTests

import blotto.domain.Player
import blotto.domain.User
import blotto.errors.player.EmailAlreadyExists
import blotto.errors.player.UsernameAlreadyExists
import blotto.service.app.PlayerService
import blotto.test.AbstractSpringTest
import junit.framework.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

public class PlayerService_createPlayer_Test extends AbstractSpringTest {

    @Autowired
    PlayerService playerService

    @Test
    public void 'creating player'() {
        given:
        playerService.createPlayer("user1", "paswd", "asd@asd.ru", "Test User")

        when:
        playerService.createPlayer("user1", "paswd", "asd@asd.ru", "Test User")
        then:
        thrown(UsernameAlreadyExists)

        when:
        playerService.createPlayer("user2", "paswd", "asd@asd.ru", "Another Test User")
        then:
        thrown(EmailAlreadyExists)

        when:
        playerService.createPlayer("user2", "paswd", "asd2@asd.ru", "Another Test User")
        then:
        def user = User.findByUsername("user2")
        Assert.assertNotNull("user", user)
        Assert.assertEquals("user name", "Another Test User", user.fullName)
        Assert.assertEquals("user email", "asd2@asd.ru", user.email)
        def player = Player.findByUser(user)
        Assert.assertNotNull("player", player)
    }

}