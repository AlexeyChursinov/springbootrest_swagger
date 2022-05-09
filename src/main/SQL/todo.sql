create table todo
(
    id        serial  not null
        constraint todo_pkey
            primary key,
    title     varchar not null,
    completed boolean not null,
    user_id   bigint  not null
        constraint todo_to_users
            references users
);

alter table todo
    owner to postgres;

create index fki_todo_to_users
    on todo (user_id);

