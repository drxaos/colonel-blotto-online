package blotto.config

server.port = 9987

app.host = "blotto.v-pp.ru"
app.url = "http://${app.host}/"
app.title = "Игра полковника Блотто"
mail.from = "admin@${app.host}"

environments {
    dev {
//        datasource.username = "sa"
//        datasource.password = "sa"
//        datasource.url = "jdbc:h2:file:~/testdb;MODE=MySQL"
//        datasource.driver = "org.h2.Driver"
        datasource.username = "root"
        datasource.password = "root"
        datasource.url = "jdbc:mysql://localhost/blotto"
        datasource.driver = "com.mysql.jdbc.Driver"
    }
    test {
        datasource.username = "sa"
        datasource.password = "sa"
        datasource.url = "jdbc:h2:mem:testing;DB_CLOSE_DELAY=-1;MODE=MySQL"
        datasource.driver = "org.h2.Driver"

        // Geb
        driver = {
            String type = System.getenv("webdriver");
            if (type == "firefox") {
                return Class.forName("org.openqa.selenium.firefox.FirefoxDriver").newInstance()
            } else {
                return Class.forName("org.openqa.selenium.chrome.ChromeDriver").newInstance()
            }
        }
        baseUrl = "http://localhost:${server.port}"
        reportsDir = new File("build/geb-reports")
        reportOnTestFailureOnly = true
    }
    prod {
        // ...
    }
}

