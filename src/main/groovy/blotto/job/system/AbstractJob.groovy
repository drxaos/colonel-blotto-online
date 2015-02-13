package blotto.job.system

import groovy.util.logging.Log4j
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.support.CronSequenceGenerator

import java.lang.reflect.Method

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
                while (lock.inProgress) {
                    Thread.sleep(1000)
                }
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

    Date getNextRun(){
        // TODO find closest with CronSequenceGenerator
        new CronSequenceGenerator("").next(new Date())
        return new Date()
    }

    private List<Method> getMethodsAnnotatedWithScheduled() {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = this.class;
        while (klass != Object.class) {
            // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(Scheduled.class)) {
                    Scheduled annotInstance = method.getAnnotation(Scheduled.class);
                    if (annotInstance.cron()) {
                        methods.add(method);
                    }
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }
}
