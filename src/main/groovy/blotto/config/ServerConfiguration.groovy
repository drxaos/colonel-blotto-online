package blotto.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.ErrorPage
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus

import java.util.concurrent.TimeUnit

/**
 * Tomcat configuration
 */
@Configuration
public class ServerConfiguration implements EmbeddedServletContainerCustomizer {

    @Value('${server.port:8080}')
    int port

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return new ServerConfiguration();
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) configurableEmbeddedServletContainer;

        tomcat.setPort(port)

        tomcat.setSessionTimeout(120, TimeUnit.MINUTES);

        tomcat.setErrorPages(new HashSet<ErrorPage>());
        tomcat.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/fail?code=400"));
        tomcat.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/fail?code=403"));
        tomcat.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/fail?code=404"));
        tomcat.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/fail?code=405"));
        tomcat.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/fail?code=500"));
        tomcat.addErrorPages(new ErrorPage("/fail"));

        tomcat.mimeMappings.add("ico", "image/x-icon")
    }
}