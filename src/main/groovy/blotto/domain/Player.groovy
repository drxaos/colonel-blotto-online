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

    Integer f1 = 0
    Integer f2 = 0
    Integer f3 = 0
    Integer f4 = 0
    Integer f5 = 0
    Integer f6 = 0
    Integer f7 = 0
    Integer f8 = 0
    Integer f9 = 0

    Integer position
    Integer wins = 0
    Integer loses = 0
    Integer draws = 0

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