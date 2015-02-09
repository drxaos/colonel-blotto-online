package blotto.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class StrategyController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = ["/strategy", "/"], method = RequestMethod.GET)
    public String strategy() {
        return "strategy/strategy"
    }

}