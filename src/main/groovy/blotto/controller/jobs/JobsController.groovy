package blotto.controller.jobs

import blotto.controller.system.AbstractMvcController
import blotto.job.system.JobManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class JobsController extends AbstractMvcController {

    // blotto.config.SchedulingConfiguration.registrar.cronTasks[0].trigger.nextExecutionTime(new org.springframework.scheduling.support.SimpleTriggerContext(new Date(), new Date(), new Date()))

    @Autowired
    JobManager jobManager

    @Secured(["ROLE_ADMIN"])
    @RequestMapping(value = "/jobs/list", method = RequestMethod.GET)
    public ModelAndView listJobs() {
        return new ModelAndView("jobs/list", [jobs: jobManager.jobs, tasks: jobManager.tasks])
    }

}
