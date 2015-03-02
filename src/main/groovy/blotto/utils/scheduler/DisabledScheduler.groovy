package blotto.utils.scheduler

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger

import java.util.concurrent.ScheduledFuture

class DisabledScheduler implements TaskScheduler {
    @Override
    ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return null
    }

    @Override
    ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        return null
    }

    @Override
    ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        return null
    }

    @Override
    ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        return null
    }

    @Override
    ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        return null
    }

    @Override
    ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        return null
    }
}
