-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: G:/dev/colonel-blotto-online/src/main/resources/liquibase/changelog.xml
-- Ran at: 23.02.15 1:51
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1424638256734
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'home-fca85f8b86 (192.168.0.100)', LOCKGRANTED = '2015-02-23 01:51:08.406' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-1::xaos
CREATE TABLE player (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, created TIMESTAMP NOT NULL, draws INT(10), email VARCHAR(255) NOT NULL, full_name VARCHAR(255) NOT NULL, loses INT(10), password VARCHAR(255) NOT NULL, position INT(10), score INT(10) NOT NULL, strategy_f1 INT(10) NOT NULL, strategy_f2 INT(10) NOT NULL, strategy_f3 INT(10) NOT NULL, strategy_f4 INT(10) NOT NULL, strategy_f5 INT(10) NOT NULL, strategy_f6 INT(10) NOT NULL, strategy_f7 INT(10) NOT NULL, strategy_f8 INT(10) NOT NULL, strategy_f9 INT(10) NOT NULL, strategy_updated TIMESTAMP, username VARCHAR(255) NOT NULL, wins INT(10), CONSTRAINT CONSTRAINT_8 PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-1', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml', NOW(), 1, '7:018022876feb7bf13ad82ee1d55a9742', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-2::xaos
ALTER TABLE player ADD CONSTRAINT uk_9glopf0unn9e6uytmk75tox52 UNIQUE (full_name);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-2', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml', NOW(), 2, '7:97769b0ab464a83351e4dbf906672394', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-3::xaos
ALTER TABLE player ADD CONSTRAINT uk_o39xn8lmj05iew7d2tgw836jy UNIQUE (username);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-3', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml', NOW(), 3, '7:9a5271ec671fed9c494c0522e9bbe94d', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml::r0.01.00-4::xaos
ALTER TABLE player ADD CONSTRAINT uk_oivbimcon0iqmb8efpv723h08 UNIQUE (email);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.00-4', 'xaos', 'G:\dev\colonel-blotto-online\src\main\resources\liquibase\updates\r0.01.00.xml', NOW(), 4, '7:cb803b7bf36014d69df3f555f52bda34', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

