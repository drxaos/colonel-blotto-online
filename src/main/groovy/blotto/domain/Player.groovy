package blotto.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class Player {
    Strategy strategy = new Strategy()
    Date strategyLastUpdated

    Integer score = 0

    Integer position
    Integer wins = 0
    Integer loses = 0
    Integer draws = 0

    User user

    static embedded = ['strategy']

    static constraints = {
        user nullable: false

        strategyLastUpdated nullable: true

        score nullable: false, min: 0
        position nullable: true
        wins nullable: true
        loses nullable: true
        draws nullable: true
    }
}