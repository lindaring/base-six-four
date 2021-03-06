use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

CREATE TABLE activate_code
(
  id      int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  code    varchar(50) UNIQUE,
  user_id int,
  FOREIGN KEY (user_id) REFERENCES users (id)
);

ALTER TABLE activate_code
  ADD COLUMN insertDate datetime;

ALTER TABLE activate_code
  ADD COLUMN approve varchar(50) UNIQUE;

/* Selects */
select *
from activate_code;
