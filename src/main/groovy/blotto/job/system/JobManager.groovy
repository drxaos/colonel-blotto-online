package blotto.job.system

import blotto.utils.scheduler.JobUtils
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.CronTrigger
import org.springframework.scheduling.support.ScheduledMethodRunnable

class JobManager {
    class Task {
        String type // cron, once, delay, rate
        CronTrigger cron
        Date start
        Long period

        ScheduledMethodRunnable runnable

        def getName() {
            return runnable.method.declaringClass.simpleName + "." + runnable.method.name
        }

        def getFullName() {
            return runnable.method.toString()
        }

        def getNextRun() {
            return cron ? JobUtils.getNextRun(cron.expression) : ""
        }

        def getExpression() {
            return cron ? cron.expression : ""
        }
    }

    class Job {
        AbstractJob target
        CronTrigger trigger
        ScheduledMethodRunnable runnable

        def getName() {
            return runnable.method.declaringClass.simpleName + "." + runnable.method.name
        }

        def getFullName() {
            return runnable.method.toString()
        }

        def getNextRun() {
            return JobUtils.getNextRun(trigger.expression)
        }

        def getLastStart() {
            return target.lastStart
        }

        def getLastEnd() {
            return target.lastEnd
        }

        def getInProgress() {
            return target.inProgress
        }

        def getExpression() {
            return trigger.expression
        }
    }

    List<Task> tasks = []
    List<Job> jobs = []

    def addCron(Runnable runnable, Trigger trigger) {
        def target = ((ScheduledMethodRunnable) runnable).target
        if (target instanceof AbstractJob) {
            jobs << new Job(runnable: (ScheduledMethodRunnable) runnable, target: target, trigger: (CronTrigger) trigger)
        } else {
            tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "cron", cron: (CronTrigger) trigger)
        }
    }

    def addOnce(Runnable runnable, Date date) {
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "once", start: date)
    }

    def addRate(Runnable runnable, Date date, long l) {
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "rate", start: date, period: l)
    }

    def addDelay(Runnable runnable, Date date, long l) {
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "delay", start: date, period: l)
    }
}
