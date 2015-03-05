package blotto.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

@Entity
@ToString
@EqualsAndHashCode
class User {
    String username
    String password
    Date created = DateTime.now().toDate()

    String fullName
    String email

    static hasOne = [player: Player]

    static constraints = {
        username nullable: false, blank: false, unique: true
        password nullable: false, blank: false

        fullName nullable: false, blank: false
        email nullable: false, blank: false, unique: true

        player nullable: true
    }
}