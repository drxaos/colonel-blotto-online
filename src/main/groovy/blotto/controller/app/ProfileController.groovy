package blotto.controller.app

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import blotto.service.app.PlayerService
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
public class ProfileController extends AbstractMvcController {
    @Autowired
    PlayerService playerService

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        def user = playerService.currentLoggedInUser
        return new ModelAndView("profile/profile", [user: user])
    }

    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer updateProfile(ProfileParams cmd) {
        action(cmd) {
            def user = playerService.currentLoggedInUser
            playerService.updateUser(user, cmd.password, cmd.email, cmd.fullName)
            return success("updated")
        }
    }

}

@Validateable
class ProfileParams {
    String password
    String fullName
    String email

    static constraints = {
        password nullable: false, blank: false
        fullName nullable: false, blank: false
        email nullable: false, blank: false
    }
}