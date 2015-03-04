package blotto.utils.scheduler

import org.joda.time.DateTime
import org.springframework.scheduling.support.CronSequenceGenerator

class JobUtils {

    static Date getNextRun(String exp) {
        return new CronSequenceGenerator(exp).next(DateTime.now().toDate())
    }
}
