package blotto.service.app

import blotto.domain.Player
import blotto.domain.Strategy
import blotto.job.app.BattleJob
import blotto.mail.app.Mailer
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

    @Autowired
    Mailer mailer

    @Transactional
    public List<Strategy> getBestStrategies(int count) {
        Player.findAll("from Player p where p.position > 0 and p.position <= :maxPos order by p.position asc, p.wins desc, p.loses asc, p.strategyLastUpdated desc", [maxPos: count], [max: count]).strategy
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
        log.info("Battle job start")
        Player.withTransaction {
            // reset positions and disable incorrect strategies
            Player.executeUpdate("update Player p set p.position = case when (" +
                    "p.strategy.f1 + p.strategy.f2 + p.strategy.f3 +" +
                    "p.strategy.f4 + p.strategy.f5 + p.strategy.f6 +" +
                    "p.strategy.f7 + p.strategy.f8 + p.strategy.f9 = 100 ) then 0 else -1 end," +
                    "p.wins = 0, p.loses = 0, p.draws = 0, p.score = 0")

            // count wins, draws and loses
            [wins: ">", loses: "<", draws: "="].each { res, op ->
                1..9.each { num ->
                    Player.executeUpdate("update Player p set p.${res} = p.${res} + " +
                            "(select count(*) from Player p2 where p <> p2 and" +
                            "p.position <> -1 and p2.position <> -1 and" +
                            "p.strategy.f${num} ${op} p2.strategy.f${num})")
                }
            }

            // calculate score
            Player.executeUpdate("update Player p set p.score = " +
                    "p.wins * 10 + p.draws * 5")

            // calculate positions


            // add points


        }
        log.info("Battle job end")
    }

}
