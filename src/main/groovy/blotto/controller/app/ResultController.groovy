package blotto.controller.app

import blotto.controller.system.AbstractMvcController
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

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView result() {
        def player = playerService.loggedInUser
        return new ModelAndView("result/result", [:])
    }

}