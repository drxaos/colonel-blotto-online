-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 23.02.15 1:51
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424638256734
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-02-23 01:51:09.343' WHERE ID = 1 AND LOCKED = FALSE;

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-4::xaos
ALTER TABLE player DROP CONSTRAINT uk_oivbimcon0iqmb8efpv723h08;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-4' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.00.xml';

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-3::xaos
ALTER TABLE player DROP CONSTRAINT uk_o39xn8lmj05iew7d2tgw836jy;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-3' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.00.xml';

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-2::xaos
ALTER TABLE player DROP CONSTRAINT uk_9glopf0unn9e6uytmk75tox52;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-2' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.00.xml';

-- Rolling Back ChangeSet: G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-1::xaos
DROP TABLE player;

DELETE FROM DATABASECHANGELOG  WHERE ID='r0.01.00-1' AND AUTHOR='xaos' AND FILENAME='G:devcolonel-blotto-onlinesrcmainresourcesliquibaseupdatesr0.01.00.xml';

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

