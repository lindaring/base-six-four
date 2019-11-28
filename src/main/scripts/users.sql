use db_base_six_four;

/* Create Database */
show databases;

create database db_base_six_four;

/* Create Table */
show tables;

insert into users
values (null, 'linda@try.com', '123', 1);

insert into users
values (null, 'sk@try.com', '123', 1);

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
