package blotto.errors.battle

import blotto.errors.system.ServiceException

class BattleIsInProgress extends ServiceException {
    BattleIsInProgress() {
        super("please-wait")
    }
}
