package blotto

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.web.BasicErrorController
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = [BasicErrorController, LiquibaseAutoConfiguration])
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    static applicationContext

    private static params = []
    static ConfigObject config

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        if (args.length >= 1) {
            app.setAdditionalProfiles(args[0])
            for (int i = 1; i < args.length; i++) {
                params << args[i]
            }
        }
        app.run(args);
    }

    public static List getParams() {
        def res = []
        res.addAll(params)
        return res
    }

    public static String resolveValue(String name) {
        ((ConfigurableBeanFactory) applicationContext.autowireCapableBeanFactory).resolveEmbeddedValue('${' + name + '}')
    }
}
