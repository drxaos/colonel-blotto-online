package blotto.controller.auth

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import blotto.errors.UserAlreadyExists
import blotto.service.app.PlayerService
import blotto.utils.SignInUtils
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
public class LoginController extends AbstractMvcController {

    @Autowired
    PlayerService playerService

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
            def player = playerService.createPlayer(cmd.username, cmd.password, cmd.email, cmd.fullName)
            SignInUtils.signin(player.username)
            return answer("registered", [redirect: "/help"])
        }
        on(UserAlreadyExists) { UserAlreadyExists e ->
            return error(e) << field("username", "already-exists")
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