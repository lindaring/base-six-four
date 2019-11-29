use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

CREATE TABLE roles
(
  id   int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  role varchar(50) UNIQUE
);

ALTER TABLE roles
  ADD COLUMN insertDate datetime;
  
/* Inserts */
INSERT INTO roles (role)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

update roles
set role = 'ADMIN'
where role = 'ROLE_ADMIN';

update roles
set role = 'USER'
where role = 'ROLE_USER';

/* Selects */
select *
from roles;
