package blotto.controller.layout

import blotto.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class LayoutModelInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    PlayerService playerService

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            def player = playerService.loggedInUser

            def model = modelAndView.modelMap
            model.player = player
        }
    }
}