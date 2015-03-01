package blotto.service.app

import blotto.aop.inprogress.DisableIfBattleInProgress
import blotto.domain.Player
import blotto.domain.Strategy
import blotto.errors.system.ServiceException
import groovy.util.logging.Log4j
import org.joda.time.DateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class StrategyService {

    @Transactional
    @DisableIfBattleInProgress
    public void updateStrategy(Player player, Strategy strategy) {
        if (!player || !strategy) {
            throw new ServiceException("illegal-arguments")
        }
        player.strategy = strategy
        player.strategyLastUpdated = DateTime.now().toDate()
        player.save(flush: true, failOnError: true)
    }

    public int countSoldiers(Strategy strategy) {
        if (!strategy) {
            throw new ServiceException("illegal-arguments")
        }
        return (1..9).collect { strategy."f${it}" }.sum(0)
    }

    public boolean checkSoldiers(Strategy strategy) {
        return countSoldiers(strategy) == 100
    }

}
