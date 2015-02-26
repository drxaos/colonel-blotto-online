package blotto.test

import blotto.Application
import blotto.domain.Player
import blotto.service.app.PlayerService
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

public class PlayerServiceTest extends AbstractSpringTest {

    @Autowired
    PlayerService playerService

    @Before
    public void setUp() {
    }

    @Test
    public void canFetchPlayer() {
        playerService.createPlayer("user1", "paswd", "asd@asd.ru", "Test User")

        def player = Player.findByUsername("user1")

        Assert.assertNotNull("player", player)
        Assert.assertEquals("player name", "Test User", player.fullName)
        Assert.assertEquals("player email", "asd@asd.ru", player.email)
    }

}