package blotto.test.serviceTests

import blotto.service.app.GameService
import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.helpers.StrategySteps
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

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
        assert p2.wins == 4
        assert p2.draws == 9
        assert p2.loses == 5
        assert p2.score == 85
        assert p2.position == 2
        p3.refresh()
        assert p3.wins == 11
        assert p3.draws == 5
        assert p3.loses == 2
        assert p3.score == 135
        assert p3.position == 1

        when: "p3 open results"
        playerSteps.login("p3")
        // to ResultPage
        then: "p3 sees results"
        // Ваша стратегия заняла 1 место, сыграв 18 игр, из них 11 победы, 2 поражений и 5 игр вничью.
    }
}