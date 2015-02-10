package blotto.controller

import blotto.domain.Player
import blotto.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class ErrorController extends AbstractMvcController {

    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public ModelAndView showError() {
        Player p = null
        try {
            p = playerService.loggedInUser
        } catch (Exception ignore) {
            // nothing
        }
        return new ModelAndView("error/error", [player: p]);
    }
}