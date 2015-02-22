package blotto.config

import blotto.errors.ServiceException
import blotto.service.app.PlayerService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * Bootstrap executes on application startup
 */
@Log4j
@Component
@Profile(["dev", "prod"])
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private boolean initialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized) {
            return;
        }
        initialized = true;
        log.info("Bootstrap")
        bootstrap()
    }

    @Autowired
    PlayerService peopleService

    public void bootstrap() {

        try {
            peopleService.createPlayer("test", "test", "test@example.com", "User for test")
        } catch (ServiceException e) {
            log.info(e)
        }

    }
}