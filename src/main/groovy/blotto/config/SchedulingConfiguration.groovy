package blotto.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture

/**
 * Scheduling configuration and task registry holder
 */
@Configuration
@EnableScheduling
class SchedulingConfiguration implements SchedulingConfigurer {

    static ScheduledTaskRegistrar registrar

    @Autowired
    ApplicationContext applicationContext

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        def prod = applicationContext.environment.activeProfiles.contains("prod")

        registrar = taskRegistrar

        if (prod) {
            taskRegistrar.setScheduler(taskExecutor());
        } else {
            taskRegistrar.setScheduler(new DisabledScheduler());
        }
    }

    @Profile("prod")
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

}

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