package blotto.config

import blotto.utils.liquibase.ReleasingLiquibase
import groovy.util.logging.Log4j
import liquibase.servicelocator.ServiceLocator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import org.springframework.util.Assert

import javax.annotation.PostConstruct
import javax.sql.DataSource
import java.sql.Driver

/**
 * Liquibase changelog generation
 */
@Log4j
@Configuration
@Profile(["liquibase"])
@ConditionalOnClass(ReleasingLiquibase)
public class LiquibaseConfiguration implements ApplicationListener {

    @Autowired
    ResourceLoader resourceLoader

    String changelog = "classpath:/liquibase/changelog.xml"

    @PostConstruct
    public void checkChangelogExists() {
        Resource resource = this.resourceLoader.getResource(changelog);
        Assert.state(resource.exists(), "Cannot find liquibase changelog");
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        serviceLocator.addPackageToScan(CommonsLoggingLiquibaseLogger.class.getPackage().getName());
    }

    @Bean
    public ReleasingLiquibase liquibase() {
        ReleasingLiquibase liquibase = new ReleasingLiquibase();
        return liquibase;
    }

    private DataSource createTmpDatasource(String id) {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource()
        simpleDriverDataSource.setUrl("jdbc:h2:mem:tmp-${id}-${System.currentTimeMillis()};DB_CLOSE_DELAY=-1;MODE=MySQL")
        simpleDriverDataSource.setUsername("sa")
        simpleDriverDataSource.setPassword("sa")
        simpleDriverDataSource.setDriverClass((Class<Driver>) Class.forName("org.h2.Driver"))
        return simpleDriverDataSource
    }

    @Bean
    public DataSource dataSource() {
        return createTmpDatasource("hibernate")
    }

    @Bean
    public DataSource dataSourceLiquibase() {
        return createTmpDatasource("liquibase")
    }

    void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            log.info("Shutting down")
            System.exit(0)
        }
    }
}
