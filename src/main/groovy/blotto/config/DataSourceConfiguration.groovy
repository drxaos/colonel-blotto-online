package blotto.config

import blotto.Application
import groovy.util.logging.Log4j
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.SimpleDriverDataSource

import javax.sql.DataSource
import java.sql.Driver

/**
 * Database config
 */
@Log4j
@Configuration
@Profile(["prod", "dev", "test"])
public class DataSourceConfiguration {

    @Value('${datasource.username:root}')
    String username
    @Value('${datasource.password:root}')
    String password
    @Value('${datasource.url:jdbc:mysql://localhost/blotto}')
    String url
    @Value('${datasource.driver:com.mysql.jdbc.Driver}')
    String driver

    String changelog = "classpath:/liquibase/changelog-classpath.xml"

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource()
        simpleDriverDataSource.setPassword(password)
        simpleDriverDataSource.setUrl(url)
        simpleDriverDataSource.setUsername(username)
        simpleDriverDataSource.setDriverClass((Class<Driver>) Class.forName(driver))
        return simpleDriverDataSource
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, ApplicationContext applicationContext) {
        def test = applicationContext.environment.acceptsProfiles("test")

        SpringLiquibase liquibase = new SpringLiquibase()
        liquibase.setChangeLog(changelog)
        liquibase.setDataSource(dataSource)
        liquibase.setDropFirst(Application.params.contains("dropAll"))
        liquibase.setShouldRun(Application.params.contains("migrate") || test)
        return liquibase
    }

}
