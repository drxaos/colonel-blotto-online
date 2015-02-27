package blotto.test

import blotto.Application
import geb.Browser
import geb.Configuration
import geb.buildadapter.BuildAdapterFactory
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
import spock.lang.Specification
import spock.lang.Stepwise

import javax.sql.DataSource

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
abstract class AbstractSpringTest extends Specification {

    // ######### Geb #########

    String gebConfEnv = "test"
    String gebConfScript = null

    static Browser _browser

    Configuration createBrowserConf() {
        def classLoader = new GroovyClassLoader(getClass().classLoader)
        def buildAdapter = BuildAdapterFactory.getBuildAdapter(classLoader)
        new Configuration(Application.config, System.properties, buildAdapter, classLoader)
    }

    Browser createBrowser() {
        new Browser(createBrowserConf())
    }

    Browser getBrowser() {
        if (_browser == null) {
            _browser = createBrowser()
        }
        _browser
    }

    void resetBrowser() {
        if (_browser?.config?.autoClearCookies) {
            _browser.clearCookiesQuietly()
        }
        _browser = null
    }

    def methodMissing(String name, args) {
        getBrowser()."$name"(*args)
    }

    def propertyMissing(String name) {
        getBrowser()."$name"
    }

    def propertyMissing(String name, value) {
        getBrowser()."$name" = value
    }

    private isSpecStepwise() {
        this.class.getAnnotation(Stepwise) != null
    }

    def setupSpec() {
        createDbDump()
    }

    def setup() {

    }

    def cleanup() {
        if (!isSpecStepwise()) {
            resetBrowser()
        }
    }

    def cleanupSpec() {
        if (isSpecStepwise()) {
            resetBrowser()
        }
    }

    // ######### Database reset #########

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
        if (!isSpecStepwise()) {
            loadH2FromBackup()
        }
    }

    @AfterClass
    static void cleanupDbDump() {
        new File(DB_BACKUP_FILE.absolutePath).delete()
        DB_BACKUP_FILE = null
    }

    private void loadH2FromBackup() {
        def sql = new Sql(dataSource)
        sql.execute("DROP ALL OBJECTS")
        sql.execute("RUNSCRIPT FROM ?", [DB_BACKUP_FILE.absolutePath])
    }

}