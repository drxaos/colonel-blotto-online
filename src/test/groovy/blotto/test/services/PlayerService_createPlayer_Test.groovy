package blotto.test.services

import blotto.domain.Player
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
        def player = Player.findByUsername("user2")
        Assert.assertNotNull("player", player)
        Assert.assertEquals("player name", "Another Test User", player.fullName)
        Assert.assertEquals("player email", "asd2@asd.ru", player.email)
    }

}