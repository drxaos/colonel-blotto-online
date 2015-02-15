package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
public class HelpController extends AbstractMvcController {

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help/help"
    }

}