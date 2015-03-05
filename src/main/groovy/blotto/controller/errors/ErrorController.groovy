package blotto.controller.errors

import blotto.controller.system.AbstractMvcController
import blotto.domain.Player
import blotto.domain.User
import blotto.service.app.PlayerService
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
        User p = null
        try {
            p = playerService.currentLoggedInUser
        } catch (Exception ignore) {
            // nothing
        }
        return new ModelAndView("error/error", [player: p]);
    }
}