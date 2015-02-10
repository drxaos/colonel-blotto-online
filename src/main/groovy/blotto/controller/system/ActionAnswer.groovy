package blotto.controller.system

class ActionAnswer {
    String alert = "none"
    String code = ""
    String message = ""
    def data

    void setCode(String code) {
        this.code = code
        this.message = "[[${code}]]" //todo i18n
    }
}
