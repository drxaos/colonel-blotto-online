package blotto.utils.liquibase

import liquibase.CatalogAndSchema
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.command.CommandExecutionException
import liquibase.command.DiffToChangeLogCommand
import liquibase.database.Database
import liquibase.diff.compare.CompareControl
import liquibase.diff.output.DiffOutputControl
import liquibase.diff.output.changelog.DiffToChangeLog
import liquibase.exception.LiquibaseException
import liquibase.ext.hibernate.database.HibernateSpringDatabase
import liquibase.ext.hibernate.database.connection.HibernateConnection
import liquibase.ext.hibernate.database.connection.HibernateDriver
import liquibase.integration.commandline.CommandLineUtils
import liquibase.integration.spring.SpringLiquibase
import liquibase.serializer.ChangeLogSerializer
import liquibase.serializer.ChangeLogSerializerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

import java.sql.Connection

class ReleasingLiquibase extends SpringLiquibase implements InitializingBean {

    /* TODO
     *
     * Generate diff between db and domain model and put as new release in changelog
     * Migrate db with changelog
     * Export release from changelog to sql
     *
     */

    String releaseName

    @Autowired
    ApplicationContext applicationContext

    private boolean includeSchema = true;
    private boolean includeCatalog = true;
    private boolean includeTablespace = true;

    @Override
    public void afterPropertiesSet() throws LiquibaseException {

        releaseName = applicationContext.getEnvironment().getProperty("application.version", "snapshot-" + (new Date()).format("yyyy-MM-dd"))

        File path = applicationContext.getResource("classpath:/").file
        while (!path.list().contains("build.gradle")) {
            path = path.parentFile
        }
        new File(path.absolutePath + "/src/main/resources/db/changelog/${releaseName}/").mkdirs()
        def changelog = new File(path.absolutePath + "/src/main/resources/db/changelog/${releaseName}/changelog.xml")
        def update = new File(path.absolutePath + "/src/main/resources/db/changelog/${releaseName}/update.sql")
        def rollback = new File(path.absolutePath + "/src/main/resources/db/changelog/${releaseName}/rollback.sql")

        Connection c = getDataSource().getConnection()
        Liquibase liquibase = createLiquibase(c)

        createChangelog(liquibase, changelog)
        generateUpdateFile(liquibase, update)
        generateRollbackFile(liquibase, rollback)
    }


    public void createChangelog(Liquibase liquibase, File output) {

        Database database = liquibase.getDatabase()
        CatalogAndSchema catalogAndSchema =
                new CatalogAndSchema(database.getDefaultCatalogName(), database.getDefaultSchemaName())
        DiffOutputControl diffOutputControl =
                new DiffOutputControl(includeCatalog, includeSchema, includeTablespace)
        DiffToChangeLog diffToChangeLog = new DiffToChangeLog(diffOutputControl)

        PrintStream printStream = new PrintStream(output)
        ChangeLogSerializer changeLogSerializer = ChangeLogSerializerFactory.instance.getSerializer("xml")

        DiffToChangeLogCommand command = new DiffToChangeLogCommand();

        def refDb = CommandLineUtils.createDatabaseObject(
                this.class.classLoader,
                "hibernate:spring:blotto.domain.Player?dialect=org.hibernate.dialect.MySQL5Dialect",
                "root",
                "root",
                HibernateDriver.class.name,
                "blotto",
                "blotto",
                true,
                true,
                HibernateSpringDatabase.class.name,
                null,
                null,
                null,
                null,
        )

        command.setReferenceDatabase(refDb)
                .setTargetDatabase(database)
                .setCompareControl(new CompareControl())
                .setOutputStream(System.out);
        command.setChangeLogFile(output.absolutePath)
                .setDiffOutputControl(diffOutputControl);

        try {
            command.execute();
        } catch (CommandExecutionException e) {
            throw new LiquibaseException(e);
        }

    }

    public void generateRollbackFile(Liquibase liquibase, File outFile) throws LiquibaseException {
        FileWriter output = new FileWriter(outFile)
        try {
            liquibase.futureRollbackSQL(null, new Contexts(getContexts()), new LabelExpression(getLabels()), output);
        } catch (IOException e) {
            throw new LiquibaseException("Unable to generate rollback file.", e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                log.severe("Error closing output", e);
            }
        }
    }


    public void generateUpdateFile(Liquibase liquibase, File outFile) throws LiquibaseException {
        FileWriter output = new FileWriter(outFile)
        try {
            liquibase.update(Integer.MAX_VALUE, new Contexts(getContexts()), new LabelExpression(getLabels()), output);
        } catch (IOException e) {
            throw new LiquibaseException("Unable to generate update file.", e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                log.severe("Error closing output", e);
            }
        }
    }

}
