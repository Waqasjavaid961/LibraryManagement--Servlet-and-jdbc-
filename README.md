                              LibraryMangement System (web based)
A complete web based LIBRARY MANGEMENT project build with java servlet and jsp and jdbc 
FEATURES ->
1 :-   AUTHENTICATION
LOGIN SYSTEM WITH SESSION MANGEMENT SYSTEM 
2 :- ROLES 
DIFFERENT ACCESS FOR USERS , ADMINS AND SUPER_ADMIN
3:- SUPER_ADMIN MANGEMENT
PROMOTE USER TO ADMIN AND  DEMOTE ADMIN TO USER
MANAGE BOOKS FEATURES 
UPDATE HER HISTORY OR DETAILS

4:- ADMIN MANGEMENT 
ADD BOOKS UPDATE BOOKS VIEW BOOKS DELETE BOOKS BLOCK USERS 
5:- USERS
BOROOW BOOKS 
RETUR BOOKS 
UPDATE PERSONAL DETAILS
VIEW HISTORY

TECHNOLOGY
FOR BACKEND 
JAVA 
SERVLET 
FRONTEND 
FRONTEND BUILD TOTALLY AI USING HTML AND CSS I JUST ONLY COPY AND PASTE AI CODE (HTML AND CSS)(JSP)
DATABASE
MYSQL VIA JDBC
sQL QUERY
drop database if exists library;
create database library;
use library;
create table users(
id int auto_increment primary key,
name varchar(100),
gmail varchar(100)unique,
password varchar(100),
role enum("admin","user","superadmin")not null,
status boolean default true
);
ALTER TABLE users 
ADD COLUMN superadmin_check CHAR(1) 
GENERATED ALWAYS AS (IF(role = 'superadmin', '1', NULL)) VIRTUAL;
ALTER TABLE users 
ADD CONSTRAINT unique_superadmin_limit UNIQUE (superadmin_check);
insert into users(name , gmail,password,role) values("waqas","waqas@gmail.com","sahara",'superadmin');
insert into users(name , gmail,password,role) values("wa","@was.c","sra",'admin');

select *from users;
create table books(
catagory enum ("computer","science","mathematics","fiction","biography"),
id int auto_increment unique,
book_name varchar(100),
ISBN bigint unique,
book_author varchar(100) not null,
edition varchar(100),
language varchar(100),
quantity int,
price bigint,
status boolean default false
);
select *from books;
create table customerdetail(
user_id int,
book_id int,
catagory varchar(100),
book_name varchar(100),

foreign key(user_id) references users(id),
foreign key (book_id)references books(id)
);
ALTER TABLE customerdetail add  column quantity int ;
alter table customerdetail add column price long;
alter table customerdetail add column total long;

select*from customerdetail;


SERVER TOMCAT 10
                          SHORT SUMMARY
                          THIS PROJECT I BUILD TO LEARN BACKEND DEVELOPEMENT ITS A AMAZNG PROJECT AND I CHANGED TIME TO TIME AND UPDATE AND ADD MORE FEATURES 
