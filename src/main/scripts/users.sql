use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

CREATE TABLE users
(
  id       int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(50) UNIQUE,
  password varchar(255)
);

ALTER TABLE users
  ADD COLUMN active int(1);

ALTER TABLE users
  ADD COLUMN insertDate datetime;

/* Inserts */
update users
set password = '$2y$05$p3yQ7ijJOwQoPhDUcYLhm.h9426rKdUdAxd69MsloFAac.1fEVNBi'
where username = 'sk@ike.com';

update users
set active = 1
where username = 'sk@ike.com';

/* Selects */
select *
from users;
