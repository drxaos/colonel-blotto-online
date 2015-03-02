package blotto.config

import blotto.utils.scheduler.DisabledScheduler
import grails.util.Holders
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Scheduling configuration and task registry holder
 */
@Log4j
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
            log.info("Setting up thread pool for job scheduling")
            taskRegistrar.setScheduler(taskExecutor());
        } else if (Holders.config.scheduler.enable) {
            log.info("Setting up default manager for job scheduling")
            taskRegistrar.setScheduler(new DefaultManagedTaskScheduler());
        } else {
            log.info("Setting up disabled job scheduling")
            taskRegistrar.setScheduler(new DisabledScheduler());
        }
    }

    @Profile("prod")
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

}
