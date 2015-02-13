package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.domain.Strategy
import blotto.job.app.BattleJob
import blotto.service.app.GameService
import blotto.service.app.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class ResultController extends AbstractMvcController {
    @Autowired
    PlayerService playerService
    @Autowired
    GameService gameService
    @Autowired
    BattleJob battleJob

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView result(Long counter) {
        if (battleJob.inProgress) {
            return new ModelAndView("result/progress", [counter: counter ?: 0])
        }
        def player = playerService.currentLoggedInUser
        def best = gameService.getBestStrategies(3)
        if (best.size() > 0) {
            while (best.size() < 3) {
                best << new Strategy()
            }
        }
        def next = gameService.getNextBattle()
        return new ModelAndView("result/result", [best: best, player: player, next: next])
    }

}