package blotto.test.web.pages

class LoginPage extends MainLayout {
    static url = "/login"
    static at = {
        assert title == "Игра полковника Блотто"
        assert currentUrl ==~ /\/login(\?.*)?/
        return true
    }
    static content = {
        heading { $("h1").text() }
        loginForm { $("form.form-signin") }

        usernameField { $("input[name=username]") }
        passwordField { $("input[name=password]") }

        submitButton(to: [StrategyPage, LoginPage]) { $("button.login__submitButton") }
    }
}