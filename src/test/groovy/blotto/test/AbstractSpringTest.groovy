package blotto.test

import blotto.Application
import groovy.sql.Sql
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import javax.sql.DataSource

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
abstract class AbstractSpringTest {
    private static File DB_BACKUP_FILE

    @Autowired
    DataSource dataSource

    @Before
    void createDbDump() {
        if (!DB_BACKUP_FILE) {
            DB_BACKUP_FILE = File.createTempFile("DB_BACKUP_FILE_" + System.currentTimeMillis(), ".sql");
            DB_BACKUP_FILE.deleteOnExit()

            def sql = new Sql(dataSource)
            sql.execute("SCRIPT TO ?", [DB_BACKUP_FILE.absolutePath])
        }
    }

    @After
    void cleanupDb() {
        loadH2FromBackup()
    }

    @AfterClass
    static void cleanupDbDump() {
        new File(DB_BACKUP_FILE.absolutePath).delete()
    }

    private void loadH2FromBackup() {
        def sql = new Sql(dataSource)
        sql.execute("DROP ALL OBJECTS")
        sql.execute("RUNSCRIPT FROM ?", [DB_BACKUP_FILE.absolutePath])
    }

}