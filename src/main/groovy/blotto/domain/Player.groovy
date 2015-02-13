package blotto.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class Player {
    String username
    String password
    Date created = new Date()

    String fullName
    String email

    Strategy strategy = new Strategy()
    Date strategyUpdated

    Integer score = 0

    Integer position
    Integer wins = 0
    Integer loses = 0
    Integer draws = 0

    static embedded = ['strategy']

    static constraints = {
        username nullable: false, blank: false, unique: true
        password nullable: false, blank: false

        fullName nullable: false, blank: false, unique: true
        email nullable: false, blank: false, unique: true

        strategyUpdated nullable: true

        score nullable: false, min: 0
        position nullable: true
        wins nullable: true
        loses nullable: true
        draws nullable: true
    }
}