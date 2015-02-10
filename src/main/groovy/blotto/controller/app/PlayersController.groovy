package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class PlayersController extends AbstractMvcController {

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public ModelAndView listPlayers() {
        return new ModelAndView("players/list", [:])
    }

}