package blotto.utils

import blotto.Application
import groovy.util.logging.Log4j
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.core.env.Environment
import org.springframework.core.io.Resource;

@Log4j
public class GroovyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    List locations
    Environment environment

    @Override
    protected void loadProperties(Properties props) throws IOException {
        ConfigObject configObject = new ConfigObject()
        List configSlurpers = (environment.activeProfiles ?: environment.defaultProfiles).collect {
            new ConfigSlurper(it)
        }
        for (def loc : locations) {
            try {
                if (loc instanceof Resource) {
                    loc = loc.getURL()
                }
                configSlurpers.each { ConfigSlurper configSlurper ->
                    def config = configSlurper.parse(loc)
                    configObject.merge(config)
                }
            } catch (IOException ignore) {
                log.warn("Cannot load config: ${loc}")
            }
        }
        props.putAll(configObject.toProperties())
        Application.config = configObject
    }


    public void setLocations(List locations) {
        this.locations = locations
    }
}