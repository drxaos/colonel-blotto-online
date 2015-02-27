package blotto.errors.system

import org.springframework.validation.BeanPropertyBindingResult

class CmdErrors extends ServiceException {
    BeanPropertyBindingResult errors

    CmdErrors(BeanPropertyBindingResult errors) {
        super("illegal-arguments")
        this.errors = errors
        //errors.allErrors.codes.flatten()
    }
}
