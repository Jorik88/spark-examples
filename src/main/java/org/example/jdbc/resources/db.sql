CREATE TABLE person
(
    id         INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    PRIMARY KEY (id)
);

insert into person(first_name,last_name) values('Ann', 'Jonson');
insert into person(first_name,last_name) values('Kat', 'Dow');
