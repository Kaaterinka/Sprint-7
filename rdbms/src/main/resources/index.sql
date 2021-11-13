--liquibase formatted sql

--changeset bolotina:index

CREATE INDEX id_fast on account1 (id)