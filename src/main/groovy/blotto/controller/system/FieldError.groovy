package blotto.controller.system

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["controller", "action", "code"])
class FieldError {

    Class controller
    String action
    def code = ""

    String name = ""
    String message


    @Override
    public String toString() {
        return "FieldError{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
