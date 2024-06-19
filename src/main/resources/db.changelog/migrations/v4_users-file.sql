-- liquibase formatted sql

-- changeset Andrey:1

create table diploma1.user_file
(
    user_username varchar(255) not null,
    user_files varchar(255) not null
);