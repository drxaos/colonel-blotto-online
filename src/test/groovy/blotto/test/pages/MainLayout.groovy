package blotto.test.pages

class MainLayout extends WebPage {
    static content = {
        alert {
            waitForAnimationComplete()
            $(".alertsHolder__container")
        }
        alertText {
            waitForAnimationComplete()
            $(".alertsHolder__container .alert__msg").text()
        }
        logout { $(".navbar__logout") }
    }
}