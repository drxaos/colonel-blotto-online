package blotto.service.app

import blotto.domain.Player
import blotto.domain.Strategy
import groovy.util.logging.Log4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class GameService {

    @Transactional
    public List<Strategy> getBestStrategies(int count) {
        Player.findAll("from Player p where p.position > 0 and p.position <= :maxPos order by p.position asc, p.wins desc, p.loses asc, p.strategyUpdated desc", [maxPos: count], [max: count]).strategy
    }

    public int getNextBattle() {
        def now = new Date()
        def elapsed = now[Calendar.MINUTE] * 60 + now[Calendar.SECOND]
        return 60 * 60 - elapsed
    }
}
