-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: liquibase/changelog.xml
-- Ran at: 3/5/15 9:11 PM
-- Against: SA@jdbc:h2:mem:tmp-liquibase-1425571863837
-- Liquibase version: 3.3.2
-- *********************************************************************

-- Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = TRUE, LOCKEDBY = 'xaos (192.168.111.18)', LOCKGRANTED = '2015-03-05 21:11:40.497' WHERE ID = 1 AND LOCKED = FALSE;

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-1::xaos
CREATE TABLE user (id BIGINT(19) AUTO_INCREMENT NOT NULL, version BIGINT(19) NOT NULL, created TIMESTAMP NOT NULL, email VARCHAR(255) NOT NULL, full_name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, username VARCHAR(255) NOT NULL, CONSTRAINT CONSTRAINT_2 PRIMARY KEY (id));

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-1', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 8, '7:39d3c2a39a6fe0a53befac23ca87d6c7', 'createTable', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-2::xaos
ALTER TABLE player ADD user_id BIGINT(19) NOT NULL;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-2', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 9, '7:9010aad511e9ce261e3069601a187248', 'addColumn', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-2-migrate-data::xaos
INSERT INTO user (version, created, email, full_name, password, username)
            SELECT 0, created, email, full_name, password, username FROM player;

update player p set p.user_id = (select u.id from user u where p.username = u.username);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-2-migrate-data', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 10, '7:f41cca55162df79edb5b4c1421089f42', 'sql', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-3::xaos
ALTER TABLE player ADD CONSTRAINT uk_fpxwfe7n29rwsbyu5p1wl2mq1 UNIQUE (user_id);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-3', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 11, '7:3d418332a4ec16d9ba003f169a5bc4ff', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-4::xaos
ALTER TABLE user ADD CONSTRAINT uk_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-4', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 12, '7:a107f268c736f8b76d116875dabac13c', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-5::xaos
ALTER TABLE user ADD CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-5', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 13, '7:2f6c28684210572cb53581df4c303b6f', 'addUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-6::xaos
ALTER TABLE player ADD CONSTRAINT fk_fpxwfe7n29rwsbyu5p1wl2mq1 FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-6', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 14, '7:aedcd9445010005e43046db077c03f33', 'addForeignKeyConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-7::xaos
ALTER TABLE player DROP CONSTRAINT uk_o39xn8lmj05iew7d2tgw836jy;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-7', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 15, '7:b3172991e6d0e35670d663eed90cf0e7', 'dropUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-8::xaos
ALTER TABLE player DROP CONSTRAINT uk_oivbimcon0iqmb8efpv723h08;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-8', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 16, '7:1d16f9e061ab5dcbbaf1426e96d2dde4', 'dropUniqueConstraint', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-9::xaos
ALTER TABLE player DROP COLUMN created;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-9', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 17, '7:ad9d51f72d56ac489d97b2852cf0dc31', 'dropColumn', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-10::xaos
ALTER TABLE player DROP COLUMN email;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-10', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 18, '7:b0f956530683534670c7e13d660ecae8', 'dropColumn', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-11::xaos
ALTER TABLE player DROP COLUMN full_name;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-11', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 19, '7:9f75476e1d44385e6c9e61585f180b8d', 'dropColumn', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-12::xaos
ALTER TABLE player DROP COLUMN password;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-12', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 20, '7:59029cbaa495a9762e70ee84b0318111', 'dropColumn', '', 'EXECUTED', '3.3.2');

-- Changeset liquibase/updates/r0.01.04.xml::r0.01.04-13::xaos
ALTER TABLE player DROP COLUMN username;

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('r0.01.04-13', 'xaos', 'liquibase/updates/r0.01.04.xml', NOW(), 21, '7:825d9c2b7dbcc8d97a35d75781b18593', 'dropColumn', '', 'EXECUTED', '3.3.2');

-- Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

