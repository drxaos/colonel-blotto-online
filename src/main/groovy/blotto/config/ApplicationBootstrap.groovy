package blotto.config

import blotto.errors.ServiceException
import blotto.service.PlayerService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Log4j
@Component
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
            peopleService.createPlayer("test", "test", "test@example.com")
        } catch (ServiceException e) {
            log.info(e)
        }

    }
}