package blotto.controller

import blotto.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class ProfileController extends WebMvcConfigurerAdapter {
    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        def player = playerService.loggedInUser
        return new ModelAndView("profile/profile", [player: player])
    }

}