--liquibase formatted sql

--changeset bolotina:init

create table account1
(
    id bigserial constraint account_pk primary key,
    amount int constraint realAmount CHECK (amount>=0),
    version int
);


