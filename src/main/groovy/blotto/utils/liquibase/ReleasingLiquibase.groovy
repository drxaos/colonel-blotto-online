package blotto.utils.liquibase

import blotto.Application
import groovy.io.FileType
import liquibase.CatalogAndSchema
import liquibase.Contexts
import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.diff.DiffGeneratorFactory
import liquibase.diff.DiffResult
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.DiffOutputControl
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.exception.DatabaseException
import liquibase.exception.LiquibaseException
import liquibase.integration.spring.SpringLiquibase
import liquibase.resource.ResourceAccessor
import liquibase.util.StringUtils
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

import javax.sql.DataSource
import java.sql.Connection

class ReleasingLiquibase extends SpringLiquibase implements InitializingBean {

    String releaseName

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    DataSource dataSourceLiquibase

    @Autowired
    DataSource dataSource

    @Override
    public void afterPropertiesSet() throws LiquibaseException {

        def cfg = applicationContext.getBean("&sessionFactory").configuration

        releaseName = applicationContext.getEnvironment().getProperty("application.version", "snapshot-" + (new Date()).format("yyyy-MM-dd"))

        File path = applicationContext.getResource("classpath:/").file
        while (!path.list().contains("build.gradle")) {
            path = path.parentFile
        }
        new File(path.absolutePath + "/src/main/resources/liquibase/updates/").mkdirs()
        def root = new File(path.absolutePath + "/src/main/resources/liquibase/changelog.xml")
        def changelog = new File(path.absolutePath + "/src/main/resources/liquibase/updates/${releaseName}.xml")
        def updateSql = new File(path.absolutePath + "/src/main/resources/liquibase/sql/${releaseName}_update.sql")
        def rollbackSql = new File(path.absolutePath + "/src/main/resources/liquibase/sql/${releaseName}_rollback.sql")

        boolean makeSql = Application.params.contains("sql")
        boolean makeDiff = Application.params.contains("diff")

        if (makeDiff) {
            changelog.delete()
        }
        if (makeSql) {
            updateSql.delete()
            rollbackSql.delete()
        }

        setChangeLog(root.absolutePath);

        Connection c = dataSourceLiquibase.getConnection()
        Liquibase liquibase = createLiquibase(c, changelog.absolutePath)
        liquibase.update(new Contexts())

        if (makeDiff) {
            DiffResult diffResult = DiffGeneratorFactory.instance.compare(
                    createDatabase(dataSource.getConnection()),
                    createDatabase(dataSourceLiquibase.getConnection()),
                    new CompareControl()
            )

            DiffOutputControl diffOutputConfig = new DiffOutputControl(false, false, false).addIncludedSchema(new CatalogAndSchema("", ""));
            def converter = new DiffToChangeLog(diffResult, diffOutputConfig)
            converter.setIdRoot(releaseName)
            converter.setChangeSetAuthor(System.getProperty("user.name") ?: "unknown")
            converter.print(new PrintStream(changelog.newOutputStream()));
        }

        if (makeSql) {
            liquibase = createLiquibase(c, "")
            liquibase.update(new Contexts(), new PrintWriter(updateSql))
            liquibase.futureRollbackSQL(null, new PrintWriter(rollbackSql))
        }
    }

    protected Liquibase createLiquibase(Connection c, String excludePath) throws LiquibaseException {
        Liquibase liquibase = new Liquibase(getChangeLog(), new FileOpener(excludePath), createDatabase(c));

        liquibase.setIgnoreClasspathPrefix(isIgnoreClasspathPrefix());
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                liquibase.setChangeLogParameter(entry.getKey(), entry.getValue());
            }
        }

        if (isDropFirst()) {
            liquibase.dropAll();
        }

        return liquibase;
    }

    protected Database createDatabase(Connection c) throws DatabaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
        if (StringUtils.trimToNull(this.defaultSchema) != null) {
            database.setDefaultSchemaName(this.defaultSchema);
        }
        database.setOutputDefaultSchema(false)
        database.setOutputDefaultCatalog(false)
        return database;
    }
}


public class FileOpener implements ResourceAccessor {

    String excludePath

    FileOpener(String excludePath) {
        this.excludePath = excludePath
    }

    @Override
    Set<InputStream> getResourcesAsStream(String path) throws IOException {
        return [new File(path).newInputStream()] as Set
    }

    @Override
    Set<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories, boolean recursive) throws IOException {
        def list = []

        new File(new File(relativeTo ?: ".").parentFile.absolutePath + "/" + path).eachFileRecurse(FileType.FILES) { File file ->
            if (file.name.endsWith(".xml") && file.absolutePath != excludePath) {
                list << file.absolutePath
            }
        }

        return list as Set
    }

    @Override
    ClassLoader toClassLoader() {
        return null
    }
}
