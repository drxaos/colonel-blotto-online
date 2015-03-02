package blotto.job.app

import blotto.job.system.AbstractJob
import blotto.service.app.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
public class BattleJob extends AbstractJob {

    @Autowired
    GameService gameService

    @Scheduled(cron = "0 0 * * * *")
    public void execute() {
        run { gameService.runBattle() }
    }
}
