package blotto.config

import blotto.controller.layout.LayoutModelInterceptor
import blotto.controller.system.MvcInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    LayoutModelInterceptor layoutModelInterceptor
    @Autowired
    MvcInterceptor mvcInterceptor

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(layoutModelInterceptor);
        registry.addInterceptor(mvcInterceptor);
    }
}
