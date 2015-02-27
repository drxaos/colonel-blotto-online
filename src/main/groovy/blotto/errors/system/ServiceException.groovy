package blotto.errors.system

class ServiceException extends RuntimeException {

    Map data

    ServiceException(String message, Map data = null, Throwable cause = null) {
        super(message, cause)
        this.data = data
    }

    @Override
    public String toString() {
        super.toString() + (data ? " " + data.toString() : "")
    }
}
