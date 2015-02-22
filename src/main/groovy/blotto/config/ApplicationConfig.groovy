package blotto.config


environments {
    dev {
        datasource.username = "sa"
        datasource.password = "sa"
        datasource.url = "jdbc:h2:file:~/testdb;MODE=MySQL"
        datasource.driver = "org.h2.Driver"
    }
    prod {
        // ...
    }
}

server.port = 9987

