-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 23.02.15 1:57
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424638631578
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-02-23 01:57:24.156' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.01.xml::r0.01.01-1::xaos
ALTER TABLE player ALTER COLUMN strategy_updated RENAME TO strategy_last_updated;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.01-1', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.01.xml', NOW(), 5, '7:cef338eb3ce2a8b08c286b3fd16f5ada', 'renameColumn', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

