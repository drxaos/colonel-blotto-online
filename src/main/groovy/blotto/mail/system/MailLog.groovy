package blotto.mail.system

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
public class MailLog implements Serializable {
    Date date
    String toEmail
    String subject
    String text
    String sender
    String view

    static constraints = {
        date nullable: true
        toEmail nullable: true
        subject nullable: true
        text nullable: true, type: 'text', maxSize: 65535
        sender nullable: true
        view nullable: true
    }

    static mapping = {
        table 'sys_mail_log'
    }
}
