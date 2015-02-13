package blotto.errors

class BattleIsInProgress extends ServiceException {
    BattleIsInProgress() {
        super("please-wait")
    }
}
