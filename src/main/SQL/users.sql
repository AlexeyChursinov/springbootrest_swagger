create table users
(
    id       serial       not null
        constraint users_pkey
            primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

alter table users
    owner to postgres;