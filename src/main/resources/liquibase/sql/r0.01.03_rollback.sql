-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 01.03.15 22:20
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425230414125
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-03-01 22:20:30.140' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.03.xml::r0.01.03-1::xaos
ALTER TABLE player ADD CONSTRAINT uk_9glopf0unn9e6uytmk75tox52 UNIQUE (full_name);

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.03-1' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.03.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

