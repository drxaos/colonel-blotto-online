/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blotto.mail

import blotto.mail.impl.DummyRequest
import blotto.mail.impl.DummyResponse
import blotto.mail.impl.DummyServletContext
import org.codehaus.groovy.grails.web.pages.GroovyPageBinding
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateRenderer
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

/**
 * Renders a GSP into the content of a mail message.
 */
@Component
class MailMessageContentRenderer {
    private static final Logger log = LoggerFactory.getLogger(MailMessageContentRenderer.class)

    @Autowired
    GroovyPagesTemplateRenderer groovyPagesTemplateRenderer

    def render(Writer out, String templateName, model, locale, String pluginName = null) {
        def request = new GrailsWebRequest(new DummyRequest(), new DummyResponse(new PrintWriter(out)), new DummyServletContext())
        def binding = new GroovyPageBinding()
        RequestContextHolder.setRequestAttributes(request)
        groovyPagesTemplateRenderer.render(request, binding, [template: templateName, model: model], "", out)
        return out.toString()
    }

}
