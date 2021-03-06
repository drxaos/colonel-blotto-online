-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: /home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 2/25/15 5:04 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424865864030
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.66)', LOCKGRANTED = '2015-02-25 17:04:30.336' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: /home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/updates/r0.01.02.xml::r0.01.02-1::xaos
DROP TABLE sys_mail_log;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.02-1' AND AUTHOR='xaos' AND FILENAME='/home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/updates/r0.01.02.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

