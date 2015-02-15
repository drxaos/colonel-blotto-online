package blotto.service.app

import blotto.domain.Player
import blotto.domain.Strategy
import blotto.job.app.BattleJob
import groovy.time.TimeCategory
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class GameService {

    @Autowired
    BattleJob battleJob

    @Transactional
    public List<Strategy> getBestStrategies(int count) {
        Player.findAll("from Player p where p.position > 0 and p.position <= :maxPos order by p.position asc, p.wins desc, p.loses asc, p.strategyUpdated desc", [maxPos: count], [max: count]).strategy
    }

    public int getNextBattle() {
        def now = new Date()
        def run = battleJob.nextRun ?: new Date()
        def elapsed = use(TimeCategory) {
            (run - now).toMilliseconds() / 1000
        }
        return elapsed
    }

    @Transactional
    public void runBattle() {
        println "Hello world"
    }
}
