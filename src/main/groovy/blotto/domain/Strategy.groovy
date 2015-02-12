package blotto.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.codehaus.groovy.grails.validation.Validateable

@Validateable
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
        f1 nullable: false, min: 0, max: 100
        f2 nullable: false, min: 0, max: 100
        f3 nullable: false, min: 0, max: 100
        f4 nullable: false, min: 0, max: 100
        f5 nullable: false, min: 0, max: 100
        f6 nullable: false, min: 0, max: 100
        f7 nullable: false, min: 0, max: 100
        f8 nullable: false, min: 0, max: 100
        f9 nullable: false, min: 0, max: 100
    }
}