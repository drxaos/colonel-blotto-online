package blotto.service

import blotto.controller.system.AbstractMvcController
import blotto.controller.system.ActionAnswer
import blotto.errors.CmdErrors
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.stereotype.Service

@Log4j
@Service
public class MessageFactoryService {

    @Autowired
    MessageSource messageSource

    private static normalizeMessage(def msg) {
        msg?.toString()?.toLowerCase()?.replaceAll("[.,-_+=!?;:)('\"]", " ")?.trim()?.replaceAll("\\s+", "-")?.replaceAll("[^a-z0-9_-]", "")?.replaceAll("[-]+", "-")?.replaceAll("^-\$", "") ?: "undefined"
    }

    private static List getCodes(String path, String type, value) {
        def codes = []
        if (value) {
            if (value instanceof Exception) {
                Class exClass = value.class
                while (exClass) {
                    codes << "controller.${path}.${type}.${exClass.simpleName}.${normalizeMessage(value.message)}"
                    codes << "controller.${path}.${type}.${exClass.simpleName}"
                    exClass = exClass.getSuperclass()
                }
            } else {
                codes << "controller.${path}.${type}.${normalizeMessage(value)}"
            }
        }
        codes << "controller.${path}.${type}"
        return codes
    }

    private searchMessage(Class controllerClass, String actionName, String type,
                          def value = null, def data) {

        if (value == null) {
            return ""
        }

        if (value instanceof CmdErrors) {
            return value.cmd.errors.allErrors.first().codes.flatten()
        }

        def args = []
        if (data instanceof Map) {
            args = data.args ?: []
        }

        def path = ""
        def codes = []
        while (controllerClass != AbstractMvcController.class) {
            path = "${controllerClass.simpleName}.${actionName}"
            codes.addAll(getCodes(path, type, value))
            path = "${controllerClass.simpleName}.any"
            codes.addAll(getCodes(path, type, value))

            controllerClass = controllerClass.getSuperclass()
        }
        path = "any.${actionName}"
        codes.addAll(getCodes(path, type, value))
        path = "any.any"
        codes.addAll(getCodes(path, type, value))

        def msg = null
        for (code in codes) {
            try {
                msg = messageSource.getMessage("" + code, args as Object[], Locale.default)
            } catch (NoSuchMessageException ignore) {
                // not found
            }
            if (msg != null && !msg.isEmpty()) {
                break
            }
        }
        return msg
    }

    private ActionAnswer updateMessage(ActionAnswer answer) {
        answer.message = searchMessage(answer.controller, answer.action, answer.alert, answer.code, answer.data)
        return answer
    }

    public ActionAnswer updateAnswerData(ActionAnswer answer, Object data) {
        answer.data = data
        updateMessage(answer)
    }

    public ActionAnswer updateAnswerType(ActionAnswer answer, String type) {
        answer.alert = type
        updateMessage(answer)
    }

    public ActionAnswer createAnswerFromData(Class controller, String action, data) {
        def answer = new ActionAnswer(controller: controller, action: action, data: data)
        updateMessage(answer)
        return answer
    }

    public ActionAnswer createAnswerFromException(Class controller, String action, Exception e, data = null) {
        def answer = new ActionAnswer(controller: controller, action: action, data: data, alert: "error", code: e)
        updateMessage(answer)
        return answer
    }

    public ActionAnswer createAnswerFromCode(Class controller, String action, String type, String code, data = null) {
        def answer = new ActionAnswer(controller: controller, action: action, data: data, alert: type, code: code)
        updateMessage(answer)
        return answer
    }
}
