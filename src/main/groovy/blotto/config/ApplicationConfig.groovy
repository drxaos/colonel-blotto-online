package blotto.config


environments {
    dev {
        datasource.username = "sa"
        datasource.password = "sa"
        datasource.url = "jdbc:h2:file:~/testdb"
        datasource.driver = "org.h2.Driver"
    }
    prod {
        prod_config = true
    }
}

server.port = 9987

datasource.username = "sa"
datasource.password = "sa"
datasource.url = "jdbc:h2:file:~/testdb"
datasource.driver = "org.h2.Driver"