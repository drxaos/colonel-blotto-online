package blotto.test.services

import blotto.service.app.GameService
import blotto.service.app.PlayerService
import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.helpers.StrategySteps
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Stepwise

@Stepwise
public class GameService_runBattle_game1_Test extends AbstractSpringTest {

    @Autowired
    PlayerSteps playerSteps
    @Autowired
    StrategySteps strategySteps

    @Autowired
    GameService gameService

    @Test
    public void 'game 1'() {
        given:
        def p1 = strategySteps.createPlayerWithStrategy("p1", 11, 11, 11, 11, 11, 11, 11, 11, 12)
        def p2 = strategySteps.createPlayerWithStrategy("p2", 12, 12, 12, 8, 11, 11, 11, 11, 12)
        def p3 = strategySteps.createPlayerWithStrategy("p3", 12, 12, 12, 0, 13, 13, 13, 13, 12)

        when:
        gameService.runBattle()

        then:
        p1.refresh()
        assert p1.wins == 2
        assert p1.draws == 6
        assert p1.loses == 10
        assert p1.score == 50
        assert p1.position == 3
        p2.refresh()
        assert p1.position == 2
        p3.refresh()
        assert p1.position == 1

    }

    @Test
    public void 'game 1 results p1'() {
        when: "p1 open results"
        playerSteps.login("p1")
        // to ResultPage
        then: "p1 sees results"
        //
    }
}