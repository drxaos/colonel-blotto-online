package blotto.config

import blotto.job.system.JobManager
import blotto.utils.scheduler.DelegatingScheduler
import grails.util.Holders
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Scheduling configuration and task registry holder
 */
@Log4j
@Configuration
@EnableScheduling
class SchedulingConfiguration implements SchedulingConfigurer {

    @Autowired
    ApplicationContext applicationContext

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        def prod = applicationContext.environment.acceptsProfiles("prod")

        if (prod) {
            log.info("Setting up thread pool for job scheduling")
            def scheduler = new ConcurrentTaskScheduler(((ScheduledExecutorService) taskExecutor()))
            taskRegistrar.setScheduler(new DelegatingScheduler(scheduler: scheduler, jobManager: jobManger()));
        } else if (Holders.config.scheduler.enable) {
            log.info("Setting up default manager for job scheduling")
            taskRegistrar.setScheduler(new DelegatingScheduler(scheduler: new DefaultManagedTaskScheduler(), jobManager: jobManger()));
        } else {
            log.info("Setting up disabled job scheduling")
            taskRegistrar.setScheduler(new DelegatingScheduler(jobManager: jobManger()));
        }
    }

    @Profile("prod")
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Bean
    public JobManager jobManger() {
        return new JobManager()
    }

    @Scheduled(fixedRate = 1234l)
    public void dummyTask() {
        // nothing
    }

    @Scheduled(fixedDelay = 55555l)
    public void dummyDelayTask() {
        // nothing
    }

    @Scheduled(cron = "0 0 * * * *")
    public void dummyCronTask() {
        // nothing
    }
}
