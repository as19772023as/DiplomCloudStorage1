-- liquibase formatted sql

-- changeset Andrey:1

create table diploma1.users
(
    username varchar(255) not null,
    password varchar(255) not null,
    primary key (username)
);