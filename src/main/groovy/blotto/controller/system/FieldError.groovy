package blotto.controller.system

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["controller", "action", "code", "data"])
class FieldError {

    Class controller
    String action
    def code = ""
    def data

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
