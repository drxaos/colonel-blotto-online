package blotto.test.web.pages

class StrategyPage extends MainLayout {
    static url = "/"
    static at = {
        assert title == "Игра полковника Блотто"
        assert currentUrl ==~ /\/(strategy(\?.*))?/
        return true
    }
    static content = {
        heading { $("h1").text() }
    }
}