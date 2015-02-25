package blotto.config


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
    prod {
        // ...
    }
}

server.port = 9987

server.name = "v-pp.ru"

mail.from = "admin@${server.name}"