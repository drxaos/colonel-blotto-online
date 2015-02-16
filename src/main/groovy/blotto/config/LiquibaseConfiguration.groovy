package blotto.config

import liquibase.integration.spring.SpringLiquibase
import liquibase.servicelocator.ServiceLocator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.util.Assert

import javax.annotation.PostConstruct
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(SpringLiquibase)
@ConditionalOnBean([DataSource, ResourceLoader])
@ConditionalOnProperty(prefix = "liquibase", name = "enabled", matchIfMissing = true)
@AutoConfigureAfter(DataSourceAutoConfiguration)
public class LiquibaseConfiguration {

    @Autowired
    ResourceLoader resourceLoader

    @Autowired
    DataSource dataSource

    String changelog = "classpath:/db/changelog/changelog.xml"

    @PostConstruct
    public void checkChangelogExists() {
        Resource resource = this.resourceLoader.getResource(changelog);
        Assert.state(resource.exists(), "Cannot find liquibase changelog");
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        serviceLocator.addPackageToScan(CommonsLoggingLiquibaseLogger.class.getPackage().getName());
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelog);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}
