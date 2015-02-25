package blotto.config

import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl

/**
 * Mail sending config
 */
@Log4j
@Configuration
public class MailConfiguration {

    @Value('${mail.host:localhost}')
    String host
    @Value('${mail.port:25}')
    Integer port
    @Value('${mail.username:root}')
    String username
    @Value('${mail.password:root}')
    String password

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl()
        mailSender.setPassword(password)
        mailSender.setUsername(username)
        mailSender.setHost(host)
        mailSender.setPort(port)
        mailSender.setDefaultEncoding("utf-8")
        mailSender.getJavaMailProperties().with { prop ->
            prop.setProperty("mail.transport.protocol", "smtp")
            prop.setProperty("mail.smtp.auth", "false")
            prop.setProperty("mail.smtp.starttls.enable", "false")
            prop.setProperty("mail.debug", "false")
        }
        return mailSender
    }
}
