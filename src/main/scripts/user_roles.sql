use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

CREATE TABLE user_roles
(
  id      int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  user_id int,
  role_id int,
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

/* Inserts */
insert into user_roles
values (null, 1, 2),
       (null, 1, 1),
       (null, 2, 1);

/* Selects */
select *
from user_roles;
