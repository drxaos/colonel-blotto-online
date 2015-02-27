package blotto.errors.player

import blotto.errors.system.ServiceException

class UsernameAlreadyExists extends ServiceException {
    UsernameAlreadyExists(String username) {
        super("user-exists", [args: [username]])
    }
}
