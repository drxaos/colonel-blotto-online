package blotto

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.web.BasicErrorController
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

import java.lang.reflect.Field
import java.nio.charset.Charset

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = [BasicErrorController, LiquibaseAutoConfiguration])
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application {

    static applicationContext
    private static params = []

    static {
        if (System.getProperty('file.encoding')?.toUpperCase() != "UTF-8") {
            System.setProperty 'file.encoding', 'UTF-8'
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null, null);
        }
    }

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
        return new ArrayList(params)
    }

    public static String resolveValue(String value) {
        ((ConfigurableBeanFactory) applicationContext.autowireCapableBeanFactory).resolveEmbeddedValue(value)
    }
}
