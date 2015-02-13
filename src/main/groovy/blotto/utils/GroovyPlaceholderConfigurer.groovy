package blotto.utils

import groovy.util.logging.Log4j
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.core.io.Resource;

@Log4j
public class GroovyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    List locations

    @Override
    protected void loadProperties(Properties props) throws IOException {
        ConfigObject configObject = new ConfigObject()
        ConfigSlurper configSlurper = new ConfigSlurper()
        for (def l : locations) {
            try {
                if (l instanceof Resource) {
                    l = l.getURL()
                }
                def config = configSlurper.parse(l)
                configObject.merge(config)
            } catch (IOException ignore) {
                log.warn("Cannot load config: ${l}")
            }
        }
        props.putAll(configObject.toProperties())
    }


    public void setLocations(List locations) {
        this.locations = locations
    }
}