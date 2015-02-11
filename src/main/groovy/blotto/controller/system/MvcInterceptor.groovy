package blotto.controller.system

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class MvcInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // fill AbstractMvcController
        if (handler && handler instanceof HandlerMethod) {
            if (handler.bean && handler.bean instanceof AbstractMvcController) {
                ((AbstractMvcController) handler.bean).actionName = handler?.method?.name
            }
        }

        return true;
    }
}