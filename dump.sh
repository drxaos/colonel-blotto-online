#!/bin/bash

echo "drop database blotto; create database blotto;" | mysql -u root -proot blotto
ssh root@blotto.v-pp.ru "mysqldump -uroot -p blotto" | mysql -u root -proot blotto
echo "update DATABASECHANGELOG set MD5SUM = NULL;" | mysql -u root -proot blotto
