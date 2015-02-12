package blotto.controller.auth

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
public class LoginController extends AbstractMvcController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false, defaultValue = "0") String error
    ) {
        return new ModelAndView("login/login", [error: error])
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        return new ModelAndView("login/signup", [:])
    }

    @RequestMapping(value = "/signup/process", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer doSignup(SignupParams cmd) {
        action(cmd) {
            // create + validate + save
            // sign in

            return success("registered", [redirect: "/"])
        }
    }

}

@Validateable
class SignupParams {
    String username
    String password
    String fullName
    String email

    static constraints = {
        username nullable: false, blank: false
        password nullable: false, blank: false
        fullName nullable: false, blank: false
        email nullable: false, blank: false
    }
}