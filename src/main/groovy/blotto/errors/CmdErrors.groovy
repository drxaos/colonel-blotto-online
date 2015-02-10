package blotto.errors

class CmdErrors extends ServiceException {
    def cmd

    CmdErrors(cmd) {
        super("illegal-arguments")
        this.cmd = cmd
        //cmd.errors.allErrors.codes.flatten()
    }
}
