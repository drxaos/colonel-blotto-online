package blotto.test.helpers

import blotto.test.AbstractSpringTest
import geb.Browser

class AbstractSteps {
    Browser getBrowser(){
        return AbstractSpringTest.getBrowserInst()
    }

    def methodMissing(String name, args) {
        getBrowser()."$name"(*args)
    }

    def propertyMissing(String name) {
        getBrowser()."$name"
    }

    def propertyMissing(String name, value) {
        getBrowser()."$name" = value
    }
}
