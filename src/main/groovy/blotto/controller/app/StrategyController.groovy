package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import blotto.domain.Strategy
import blotto.service.app.PlayerService
import blotto.service.app.StrategyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
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

    @Autowired
    ApplicationContext applicationContext

    @RequestMapping(value = ["/strategy", "/"], method = RequestMethod.GET)
    public ModelAndView strategy() {
        def user = playerService.currentLoggedInUser
        return new ModelAndView("strategy/strategy", [strategy: user.player.strategy])
    }

    @RequestMapping(value = "/strategy/update", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer update(Strategy strategy) {
        action(strategy) {
            def user = playerService.currentLoggedInUser
            strategyService.updateStrategy(user.player, strategy)
            if (strategyService.checkSoldiers(user.player.strategy)) {
                return success("done")
            } else {
                return warning("done-incorrect")
            }
        }
    }

//    @RequestMapping(value = "/strategy/import", method = RequestMethod.GET)
//    @ResponseBody
//    public ActionAnswer importFromCsv() {
//        action {
//            def csv = 'https://d396qusza40orc.cloudfront.net/gt/tournaments/results/blotto2_results.csv'.toURL().text
//            def num = 1
//            csv.eachLine {
//                def strategyStr = it.replaceFirst(/^"/, "").replaceFirst(/".+$/, "")
//                if (!strategyStr.contains(",")) {
//                    return
//                }
//                def player = playerService.createPlayer("a" + num, "passwd", "a" + num + "@test.ru", "a" + num)
//                def strategy = new Strategy()
//                strategyStr.split(',').eachWithIndex { String entry, int i ->
//                    strategy."f${i + 1}" = entry.trim() as int
//                }
//                strategyService.updateStrategy(player, strategy)
//                num++
//            }
//            return success("ok")
//        }
//    }

}