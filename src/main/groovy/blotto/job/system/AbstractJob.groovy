package blotto.job.system

import groovy.util.logging.Log4j

@Log4j
public class AbstractJob {
    final private lock = [inProgress: false]
    private Date lastStart = null
    private Date lastEnd = null

    public boolean isInProgress() {
        synchronized (lock) {
            return lock.inProgress
        }
    }

    Date getLastStart() {
        return lastStart
    }

    Date getLastEnd() {
        return lastEnd
    }

    public void run(Closure job) {
        try {
            synchronized (lock) {
                lock.inProgress = true
            }
            lastStart = new Date()

            job.call()

            lastEnd = new Date()
        } catch (Exception e) {
            log.error(null, e)
        } finally {
            synchronized (lock) {
                lock.inProgress = false
            }
        }
    }
}
