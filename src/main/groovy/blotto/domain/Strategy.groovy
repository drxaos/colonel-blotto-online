package blotto.domain

import grails.persistence.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@ToString
@EqualsAndHashCode
class Strategy {

    Integer f1 = 0
    Integer f2 = 0
    Integer f3 = 0
    Integer f4 = 0
    Integer f5 = 0
    Integer f6 = 0
    Integer f7 = 0
    Integer f8 = 0
    Integer f9 = 0

    static constraints = {
        f1 min: 0, max: 100
        f2 min: 0, max: 100
        f3 min: 0, max: 100
        f4 min: 0, max: 100
        f5 min: 0, max: 100
        f6 min: 0, max: 100
        f7 min: 0, max: 100
        f8 min: 0, max: 100
        f9 min: 0, max: 100
    }

    static mapping = {
        datasource "--!embedded entity"
    }
}