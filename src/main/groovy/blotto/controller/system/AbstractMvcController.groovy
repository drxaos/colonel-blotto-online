package blotto.controller.system

import blotto.errors.CmdErrors
import blotto.service.MessageFactoryService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired

@Log4j
abstract class AbstractMvcController {

    @Autowired
    MessageFactoryService messageFactoryService

    def actionName
    boolean inAction = false
    def actionResult = null
    def actionOutput = null

    final def action(Closure body) {
        action(null, body)
    }

    final def action(def cmd, Closure body) {
        if (inAction) {
            return body()
        }
        actionResult = null
        actionOutput = null
        inAction = true

        try {
            if (cmd != null && !cmd.validate()) {
                actionResult = new CmdErrors(cmd.errors)
                actionOutput = error(actionResult)
            } else {
                try {
                    actionResult = body()
                    actionOutput = answer(actionResult)
                } catch (Exception e) {
                    actionResult = e
                    actionOutput = error(e)
                }
            }
            return actionOutput
        } catch (Exception e) {
            log.error("controller error", e)
        } finally {
            inAction = false
        }
    }

    final def on(Class<? extends Exception> exception, Closure modify) {
        if (exception.isAssignableFrom(actionResult)) {
            actionOutput = answer(modify())
        }
    }

    final def answer(obj, data = null) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromException(this.class, actionName, obj, data)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = messageFactoryService.updateMessage(obj)
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromCode(this.class, actionName, "none", "" + obj, data)
            return actionOutput
        }
    }

    final def error(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "error")
    }

    final def warning(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "warning")
    }

    final def success(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "success")
    }

    final def info(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "info")
    }
}
