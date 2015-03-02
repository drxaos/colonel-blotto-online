package blotto.config

import blotto.errors.system.ServiceException
import blotto.service.app.PlayerService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * Bootstrap executes on application startup
 */
@Log4j
@Component
@Profile(["dev", "prod", "test"])
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ApplicationContext applicationContext

    private boolean initialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized) {
            return;
        }
        initialized = true;
        def profiles = applicationContext.environment.activeProfiles
        log.info("Active profiles: ${profiles}")
        log.info("Bootstrap")
        bootstrap(profiles as List)
    }

    @Autowired
    PlayerService peopleService

    public void bootstrap(List profiles) {

        if (profiles.contains("dev")) {
            try {
                peopleService.createPlayer("test", "test", "test@example.com", "User for test")
            } catch (ServiceException e) {
                log.info(e)
            }
        }
    }
}