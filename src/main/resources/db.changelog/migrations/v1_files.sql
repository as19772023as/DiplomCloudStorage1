-- liquibase formatted sql

-- changeset Andrey:1

create table if not exists diploma1.files
(
    filename varchar(255) not null,
    date timestamp(6) not null,
    file_content oid not null,
    size bigint not null,
    user_username varchar(255),
    primary key (filename)
);