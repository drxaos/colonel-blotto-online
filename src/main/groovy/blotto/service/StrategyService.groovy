package blotto.service

import blotto.domain.Player
import blotto.domain.Strategy
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class StrategyService {

    @Transactional
    public void updateStrategy(Player player, Strategy strategy) {
    }

}
