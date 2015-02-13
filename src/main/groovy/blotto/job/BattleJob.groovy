package blotto.job

import groovy.util.logging.Log4j
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Log4j
@Service
@EnableScheduling
public class BattleJob {
    final private lock = [inProgress: false]

    public boolean isInProgress() {
        synchronized (lock) {
            return lock.inProgress
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void runBattle() {
        try {
            synchronized (lock) {
                lock.inProgress = true
            }

            System.out.println("Job is running");

        } catch (Exception e) {
            log.error(null, e)
        } finally {
            synchronized (lock) {
                lock.inProgress = false
            }
        }
    }
}
