create database db_base_six_four;

use db_base_six_four;

CREATE TABLE visitors (
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    ip varchar(255),
    browser varchar(255)
);