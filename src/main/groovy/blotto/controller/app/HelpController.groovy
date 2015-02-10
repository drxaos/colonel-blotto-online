package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class HelpController extends AbstractMvcController {

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help/help"
    }

}