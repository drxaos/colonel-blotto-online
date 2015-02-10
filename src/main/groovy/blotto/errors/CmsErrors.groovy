package blotto.errors

class CmsErrors extends ServiceException {
    def errors

    CmsErrors(errors) {
        super("illegal-arguments")
        this.errors = errors
    }
}
