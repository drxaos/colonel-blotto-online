-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 23.02.15 1:57
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424638631578
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-02-23 01:57:25.468' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.01.xml::r0.01.01-1::xaos
ALTER TABLE player ALTER COLUMN strategy_last_updated RENAME TO strategy_updated;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.01-1' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.01.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

