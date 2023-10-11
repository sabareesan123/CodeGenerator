create schema IF NOT EXISTS codeGenerator;
use  codeGenerator;
create table IF NOT EXISTS  codeGenerator.UserInfo (id int auto_increment,name varchar(20),email varchar(20),password varchar(60),roles varchar(20), PRIMARY KEY (id));


    