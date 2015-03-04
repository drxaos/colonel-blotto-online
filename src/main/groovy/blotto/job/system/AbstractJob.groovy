package blotto.job.system

import blotto.Application
import blotto.utils.scheduler.JobUtils
import groovy.util.logging.Log4j
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager

import java.lang.reflect.Method

@Log4j
@Profile(["dev", "prod"])
abstract public class AbstractJob {
    final private lock = [inProgress: false]
    private Date lastStart = null
    private Date lastEnd = null
    public boolean disabled = false

    @Autowired
    private PlatformTransactionManager transactionManager;

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
        if (disabled) {
            return
        }
        try {
            synchronized (lock) {
                while (lock.inProgress) {
                    Thread.sleep(1000)
                }
                lock.inProgress = true
            }
            lastStart = DateTime.now().toDate()

            job.call()

            lastEnd = DateTime.now().toDate()
        } catch (Exception e) {
            log.error(null, e)
        } finally {
            synchronized (lock) {
                lock.inProgress = false
            }
        }
    }

    Date getNextRun() {
        def expressions = getCronExpressions()
        def next = null
        expressions.each { exp ->
            def d = JobUtils.getNextRun(exp)
            if (!next || next > d) {
                next = d
            }
        }
        return next
    }

    List<String> getCronExpressions() {
        final List<String> expressions = new ArrayList<String>();
        Class<?> klass = this.class;
        while (klass != Object.class) {
            // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(Scheduled.class)) {
                    Scheduled annotInstance = method.getAnnotation(Scheduled.class);
                    if (annotInstance.cron()) {
                        expressions.add(Application.resolveValue(annotInstance.cron()));
                    }
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return expressions;
    }
}
