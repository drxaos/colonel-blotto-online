package blotto.test.pages

import geb.Page

public class WebPage extends Page {

    def getCurrentUrl() {
        return new URL(driver.currentUrl).file
    }

    public void waitForAnimationComplete() {
        waitFor { js.exec('return $(":animated").size() == 0;') }
    }
}
