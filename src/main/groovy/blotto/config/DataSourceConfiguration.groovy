package blotto.config

import groovy.util.logging.Log4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.SimpleDriverDataSource

import javax.sql.DataSource
import java.sql.Driver

/**
 * Database config
 */
@Log4j
@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource()
        simpleDriverDataSource.setPassword("root")
        simpleDriverDataSource.setUrl("jdbc:mysql://localhost/blotto")
        simpleDriverDataSource.setUsername("root")
        simpleDriverDataSource.setDriverClass((Class<Driver>) Class.forName("com.mysql.jdbc.Driver"));
        return simpleDriverDataSource
    }

}
