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

    final def answer(obj) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = error(obj)
            return actionOutput
        } else if (obj instanceof CmdErrors) {
            actionResult = obj
            actionOutput = error(obj)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = messageFactoryService.updateAnswerData(obj, obj.data)
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromData(this.class, actionName, obj)
            return actionOutput
        }
    }

    final def error(obj, data = null) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromException(this.class, actionName, obj, data)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = messageFactoryService.updateAnswerType(obj, "error")
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromCode(this.class, actionName, "error", "" + obj, data)
            return actionOutput
        }
    }

    final def success(obj, data = null) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromException(this.class, actionName, obj, data)
            actionOutput = messageFactoryService.updateAnswerType(actionOutput, "success")
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = messageFactoryService.updateAnswerType(actionOutput, "success")
            actionOutput = obj
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromCode(this.class, actionName, "success", "" + obj, data)
            return actionOutput
        }
    }
}