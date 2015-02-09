package blotto.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Controller
public class ResultController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String result() {
        return "result/result"
    }

}