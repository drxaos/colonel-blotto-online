package blotto.controller

import blotto.domain.Strategy
import blotto.service.PlayerService
import blotto.service.StrategyService
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
        def player = playerService.loggedInUser
        return new ModelAndView("strategy/strategy", [player: player])
    }

    @RequestMapping(value = "/strategy/update", method = RequestMethod.GET)
    @ResponseBody
    public Map update(Strategy strategy) {
        action(strategy) {
            def player = playerService.loggedInUser
            strategyService.updateStrategy(player, strategy)
            return success("updated")
        }
    }

}