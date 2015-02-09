package blotto.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class ProfileController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile/profile"
    }

}