package blotto.config

import blotto.utils.GroovyPlaceholderConfigurer
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfiguration implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Bean
    public GroovyPlaceholderConfigurer placeHolderConfigurer() {
        def homeDirectory = applicationContext.getEnvironment().getProperty("user.home")
        def appName = applicationContext.getEnvironment().getProperty("application.name")

        return new GroovyPlaceholderConfigurer(locations: [
                blotto.config.ApplicationConfig.class,
                applicationContext.getResource("classpath:blotto/config/application.groovy"),
                applicationContext.getResource("file://${homeDirectory}/.config/${appName}.groovy"),
                applicationContext.getResource("file://${homeDirectory}/.${appName}/config.groovy"),
        ])
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }
}
