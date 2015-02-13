package blotto.job.app

import blotto.job.system.AbstractJob
import groovy.util.logging.Log4j
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Log4j
@Service
@EnableScheduling
public class BattleJob extends AbstractJob {

    @Scheduled(cron = "0 * * * * *")
    public void runBattle() {
        run {
            System.out.println("Job is running");
        }
    }
}
