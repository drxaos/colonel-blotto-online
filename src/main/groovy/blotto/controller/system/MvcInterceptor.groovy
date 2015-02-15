package blotto.controller.system

import blotto.service.app.PlayerService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
@Component
public class MvcInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    PlayerService playerService

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler && handler instanceof HandlerMethod) {
            if (handler.bean && handler.bean instanceof AbstractMvcController) {
                def targetController = ((AbstractMvcController) handler.bean)
                def actionName = handler?.method?.name
                targetController.actionName = actionName
                def player = playerService.currentLoggedInUser
                log.debug("${player?.username}(${request?.session?.id})>> ${request?.requestURI} :: ${targetController?.class?.simpleName}.${actionName}")
            }
        }

        return true;
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (handler && handler instanceof HandlerMethod) {
            if (handler.bean && handler.bean instanceof AbstractMvcController) {
                def targetController = ((AbstractMvcController) handler.bean)
                def player = playerService.currentLoggedInUser
                def result = modelAndView
                if (handler?.method?.returnType == ActionAnswer) {
                    result = targetController.actionOutput
                }
                log.debug("${player?.username}(${request?.session?.id})<< ${request?.requestURI} :: ${result}")
            }
        }

    }
}