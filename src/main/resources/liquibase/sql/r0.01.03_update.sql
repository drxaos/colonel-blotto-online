-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 01.03.15 22:20
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425230414125
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-03-01 22:20:28.531' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.03.xml::r0.01.03-1::xaos
ALTER TABLE player DROP CONSTRAINT uk_9glopf0unn9e6uytmk75tox52;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.03-1', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.03.xml', NOW(), 7, '7:58dca2f727019613a3d4214019f5671a', 'dropUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

