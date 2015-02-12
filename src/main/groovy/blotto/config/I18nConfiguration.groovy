package blotto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.AbstractMessageSource
import org.springframework.context.support.ResourceBundleMessageSource

/**
 * Enable messages.properties
 */
@Configuration
public class I18nConfiguration {

    @Bean
    public AbstractMessageSource servletContainerCustomizer() {
        return new ResourceBundleMessageSource(basename: "messages");
    }

}