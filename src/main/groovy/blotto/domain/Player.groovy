package blotto.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.joda.time.DateTime

@Entity
@ToString
@EqualsAndHashCode
class Player {
    String username
    String password
    Date created = DateTime.now().toDate()

    String fullName
    String email

    Strategy strategy = new Strategy()
    Date strategyLastUpdated

    Integer score = 0

    Integer position
    Integer wins = 0
    Integer loses = 0
    Integer draws = 0

    static embedded = ['strategy']

    static constraints = {
        username nullable: false, blank: false, unique: true
        password nullable: false, blank: false

        fullName nullable: false, blank: false
        email nullable: false, blank: false, unique: true

        strategyLastUpdated nullable: true

        score nullable: false, min: 0
        position nullable: true
        wins nullable: true
        loses nullable: true
        draws nullable: true
    }
}