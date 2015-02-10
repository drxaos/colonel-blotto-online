package blotto.controller

import blotto.errors.CmdErrors
import groovy.util.logging.Log4j
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Log4j
abstract class AbstractMvcController extends WebMvcConfigurerAdapter {

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
        } else if (obj instanceof CmdErrors) {// todo
            actionResult = obj
            actionOutput = error(obj)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = obj
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = new ActionAnswer(data: obj)
            return actionOutput
        }
    }

    final def error(obj) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = new ActionAnswer(data: null, alert: "error", code: obj.message)
            return actionOutput
        } else if (obj instanceof CmdErrors) {// todo
            actionResult = obj
            actionOutput = new ActionAnswer(data: null, alert: "error", code: obj.message)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            obj.alert = "error"
            actionOutput = obj
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = new ActionAnswer(data: obj, alert: "error", code: "" + obj)
            return actionOutput
        }
    }

    final def success(msg, obj = null) {
        if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = new ActionAnswer(data: null, alert: "success", code: msg)
            return actionOutput
        } else if (obj instanceof CmdErrors) { // todo
            actionResult = obj
            actionOutput = new ActionAnswer(data: null, alert: "success", code: msg)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            obj.alert = "success"
            obj.code = msg
            actionOutput = obj
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = new ActionAnswer(data: obj, alert: "success", code: msg)
            return actionOutput
        }
    }
}
