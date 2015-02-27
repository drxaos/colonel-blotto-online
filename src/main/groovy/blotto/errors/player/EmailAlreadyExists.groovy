package blotto.errors.player

import blotto.errors.system.ServiceException

class EmailAlreadyExists extends ServiceException {
    EmailAlreadyExists(String email) {
        super("email-exists", [args: [email]])
    }
}
