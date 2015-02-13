package blotto.controller.system

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(value = ["controller", "action", "code"])
class ActionAnswer {

    Class controller
    String action

    String alert = "none"
    def code = ""

    String message = ""
    List fields = []

    def data

    def leftShift(FieldError fieldError) {
        this.fields.removeAll { it.name == fieldError.name }
        this.fields << fieldError
        return this
    }


    @Override
    public String toString() {
        return "ActionAnswer{" +
                "alert='" + alert + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", fields=" + fields +
                ", data=" + data +
                '}';
    }
}
