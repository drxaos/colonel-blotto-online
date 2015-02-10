package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
public class ProfileController extends AbstractMvcController {
    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile/profile"
    }

}