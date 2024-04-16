create table book_record
(
    uuid        uuid
        constraint book_record_book_uuid_fk
            references book,
    get_time    time with time zone,
    return_time time with time zone,
    book_uuid   uuid
);

alter table book_record
    owner to postgres;
