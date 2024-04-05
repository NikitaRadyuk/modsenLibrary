create table book
(
    uuid        uuid not null
        constraint book_pk
            primary key,
    isbn        text,
    title       text,
    genre       text,
    description text,
    author      text,
    dt_create   time with time zone,
    dt_update   time with time zone,
    status      text
);

alter table book
    owner to postgres;

