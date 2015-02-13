package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.service.app.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class PlayersController extends AbstractMvcController {

    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public ModelAndView listPlayers() {
        def player = playerService.currentLoggedInUser
        def list = playerService.listPlayers(player)
        return new ModelAndView("players/list", [list: list, player: player])
    }

}