create database db_base_six_four;

show tables;

use db_base_six_four;

CREATE TABLE visitors (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ip varchar(255),
    browser varchar(255)
);

ALTER TABLE visitors ADD COLUMN insertDate DATETIME;
ALTER TABLE visitors ADD COLUMN url VARCHAR(255);
ALTER TABLE visitors ADD COLUMN location varchar(255);

CREATE TABLE users (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username varchar(50) UNIQUE,
    password varchar(255)
);

ALTER TABLE users ADD COLUMN active int(1);

CREATE TABLE roles (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    role varchar(50) UNIQUE
);

CREATE TABLE user_roles (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    role_id int,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (role)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');

select * from users;
select * from roles;
select * from user_roles;

insert into users values (null, 'linda@try.com', '123', 1);
insert into users values (null, 'sk@try.com', '123', 1);
insert into user_roles values (null, 1, 2), (null, 1, 1), (null, 2, 1);

update users set password = '$2y$05$p3yQ7ijJOwQoPhDUcYLhm.h9426rKdUdAxd69MsloFAac.1fEVNBi' where username = 'sk@ike.com';
update users set active = 1 where username = 'sk@ike.com';

update roles set role = 'ADMIN' where role = 'ROLE_ADMIN';
update roles set role = 'USER' where role = 'ROLE_USER';
