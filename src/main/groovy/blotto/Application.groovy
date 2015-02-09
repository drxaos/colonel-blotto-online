package blotto

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.security.web.savedrequest.HttpSessionRequestCache

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

}