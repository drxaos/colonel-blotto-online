package blotto.errors

class UserAlreadyExists extends ServiceException {
    UserAlreadyExists(String username) {
        super("user-exists", [args: [username]])
    }
}
