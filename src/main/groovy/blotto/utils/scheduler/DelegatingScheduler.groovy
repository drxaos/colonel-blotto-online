package blotto.utils.scheduler

import blotto.job.system.JobManager
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger

import java.util.concurrent.ScheduledFuture

class DelegatingScheduler implements TaskScheduler {
    TaskScheduler scheduler
    JobManager jobManager

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        jobManager?.addCron(task, trigger)
        return scheduler?.schedule(task, trigger)
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        jobManager?.addOnce(task, startTime)
        return scheduler?.schedule(task, startTime)
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        jobManager?.addRate(task, startTime, period)
        return scheduler?.scheduleAtFixedRate(task, startTime, period)
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        jobManager?.addRate(task, new Date(), period)
        return scheduler?.scheduleAtFixedRate(task, period)
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        jobManager?.addDelay(task, startTime, delay)
        return scheduler?.scheduleWithFixedDelay(task, startTime, delay)
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        jobManager?.addDelay(task, new Date(), delay)
        return scheduler?.scheduleWithFixedDelay(task, delay)
    }
}
