package blotto.config

import blotto.domain.Player
import blotto.domain.User
import blotto.service.app.PlayerService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest;

/**
 * Error page 500
 */
@Log4j
@ControllerAdvice
class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "/error/error";

    @Autowired
    PlayerService playerService

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        log.error(null, e)

        User u = null
        try {
            u = playerService.currentLoggedInUser
        } catch (Exception ignore) {
            // nothing
        }

        // Otherwise setup and send the user to a default error-view.
        return new ModelAndView(DEFAULT_ERROR_VIEW, [user: u, exception: e, url: req.getRequestURL()]);
    }
}