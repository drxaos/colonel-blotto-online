package blotto.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class LoginController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login/login"
    }

}