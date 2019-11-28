use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

CREATE TABLE visitors
(
  id      int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  ip      varchar(255),
  browser varchar(255)
);

ALTER TABLE visitors
  ADD COLUMN insertDate DATETIME;

ALTER TABLE visitors
  ADD COLUMN url VARCHAR(255);

ALTER TABLE visitors
  ADD COLUMN location varchar(255);
