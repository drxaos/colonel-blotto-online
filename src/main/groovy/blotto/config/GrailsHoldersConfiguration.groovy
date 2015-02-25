package blotto.config

import grails.util.Holders
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.core.io.support.GrailsFactoriesLoader
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.codehaus.groovy.grails.support.GrailsApplicationDiscoveryStrategy
import org.codehaus.groovy.grails.validation.ConstraintsEvaluator
import org.codehaus.groovy.grails.validation.ConstraintsEvaluatorFactoryBean
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

/**
 * Workaround for "Could not find ApplicationContext, configure Grails correctly first" when using @Validateable
 */
@Configuration
class GrailsHoldersConfiguration implements ApplicationContextAware, GrailsApplicationAware {

    static ApplicationContext applicationContext
    static GrailsApplication grailsApplication

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GrailsHoldersConfiguration.applicationContext = applicationContext
        Holders.addApplicationDiscoveryStrategy(new GrailsApplicationDiscoveryStrategy() {
            @Override
            GrailsApplication findGrailsApplication() {
                if (GrailsHoldersConfiguration.grailsApplication == null) {
                    Map<String, GrailsApplication> grailsApplicationBeans = findApplicationContext().getBeansOfType(GrailsApplication.class);
                    if (!grailsApplicationBeans.isEmpty()) {
                        setGrailsApplication(grailsApplicationBeans.values().iterator().next())
                    }
                }
                return GrailsHoldersConfiguration.grailsApplication
            }

            @Override
            ApplicationContext findApplicationContext() {
                return GrailsHoldersConfiguration.applicationContext
            }
        })
    }

    @Override
    void setGrailsApplication(GrailsApplication grailsApplication) {
        GrailsHoldersConfiguration.grailsApplication = grailsApplication
    }

    @Bean(name = ConstraintsEvaluator.BEAN_NAME)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ConstraintsEvaluator constraintsEvaluator() {
        return GrailsFactoriesLoader.loadFactory(ConstraintsEvaluator.class);
    }

    @Bean
    public ConstraintsEvaluatorFactoryBean constraintsEvaluatorFactoryBean() {
        return new ConstraintsEvaluatorFactoryBean()
    }
}
