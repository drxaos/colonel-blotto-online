-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: /home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 2/24/15 8:18 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424791097377
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.66)', LOCKGRANTED = '2015-02-24 20:18:28.860' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset /home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/updates/r0.01.02.xml::r0.01.02-1::xaos
CREATE TABLE sys_mail_log (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, date TIMESTAMP, sender VARCHAR(255), subject VARCHAR(255), text VARCHAR(255), to_email VARCHAR(255), CONSTRAINT CONSTRAINT_A PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.02-1', 'xaos', '/home/xaos/workspace/blotto/colonel-blotto-online/src/main/resources/liquibase/updates/r0.01.02.xml', NOW(), 6, '7:093439b7f2943042c41ec2125ed4d0d2', 'createTable', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

