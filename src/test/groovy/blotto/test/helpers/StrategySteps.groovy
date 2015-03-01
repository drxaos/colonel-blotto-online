package blotto.test.helpers

import blotto.domain.Player
import blotto.domain.Strategy
import blotto.service.app.StrategyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StrategySteps extends AbstractSteps {

    @Autowired
    PlayerSteps playerSteps

    @Autowired
    StrategyService strategyService

    Player createPlayerWithStrategy(String username, f1, f2, f3, f4, f5, f6, f7, f8, f9) {
        def player = playerSteps.createPlayer(username)
        strategyService.updateStrategy(
                player,
                new Strategy(
                        f1: f1, f2: f2, f3: f3,
                        f4: f4, f5: f5, f6: f6,
                        f7: f7, f8: f8, f9: f9,
                )
        )
        return player
    }
}
