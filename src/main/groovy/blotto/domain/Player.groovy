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
    String email

    Integer winCounter = 0

    Strategy strategy = new Strategy()

    Integer position
    Integer wins = 0
    Integer loses = 0
    Integer draws = 0

    static embedded = ['strategy']

    static constraints = {
        username blank: false
        password blank: false
        email blank: false

        winCounter min: 0
        position nullable: true
        wins nullable: true
        loses nullable: true
        draws nullable: true
    }
}