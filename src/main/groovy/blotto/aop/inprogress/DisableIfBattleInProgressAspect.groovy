package blotto.aop.inprogress

import blotto.errors.BattleIsInProgress
import blotto.job.app.BattleJob
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class DisableIfBattleInProgressAspect {

    @Autowired
    BattleJob battleJob

    @Around("@annotation(disableIfBattleInProgress)")
    public Object checkAccess(ProceedingJoinPoint pjp, DisableIfBattleInProgress disableIfBattleInProgress) throws Throwable {
        if (battleJob.inProgress) {
            throw new BattleIsInProgress()
        }
        return pjp.proceed()
    }

}