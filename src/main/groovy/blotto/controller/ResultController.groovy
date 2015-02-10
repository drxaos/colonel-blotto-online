package blotto.controller

import blotto.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class ResultController extends WebMvcConfigurerAdapter {
    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ModelAndView result() {
        def player = playerService.loggedInUser
        return new ModelAndView("result/result", [player: player])
    }

}