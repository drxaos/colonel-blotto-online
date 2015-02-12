package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import blotto.domain.Strategy
import blotto.service.app.PlayerService
import blotto.service.app.StrategyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
public class StrategyController extends AbstractMvcController {

    @Autowired
    PlayerService playerService
    @Autowired
    StrategyService strategyService

    @RequestMapping(value = ["/strategy", "/"], method = RequestMethod.GET)
    public ModelAndView strategy() {
        def player = playerService.currentLoggedInUser
        return new ModelAndView("strategy/strategy", [player: player])
    }

    @RequestMapping(value = "/strategy/update", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer update(Strategy strategy) {
        action(strategy) {
            def player = playerService.currentLoggedInUser
            strategyService.updateStrategy(player, strategy)
            if (strategyService.checkSoldiers(player.strategy)) {
                return success("done")
            } else {
                return warning("done-incorrect")
            }
        }
    }

}