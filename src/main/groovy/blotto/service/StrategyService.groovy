package blotto.service

import blotto.domain.Player
import blotto.domain.Strategy
import blotto.errors.ServiceException
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class StrategyService {

    @Transactional
    public void updateStrategy(Player player, Strategy strategy) {
        if (!player || !strategy) {
            throw new ServiceException("illegal-arguments")
        }
        player.strategy = strategy
        player.strategyUpdated = new Date()
        player.save(flush: true, failOnError: true)
    }

}
