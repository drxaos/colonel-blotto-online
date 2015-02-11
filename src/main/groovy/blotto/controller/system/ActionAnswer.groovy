package blotto.controller.system

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["controller", "action", "code"])
class ActionAnswer {

    Class controller
    String action

    String alert = "none"
    def code = ""

    String message = ""
    List fieldError = []

    def data
}
