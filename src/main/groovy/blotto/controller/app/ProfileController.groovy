package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.service.app.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class ProfileController extends AbstractMvcController {
    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        def player = playerService.currentLoggedInUser
        return new ModelAndView("profile/profile", [player: player])
    }

}