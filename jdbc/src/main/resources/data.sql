create table customer
(
    id bigint not null,
    name varchar(255) not null,
    created_at timestamp,
    primary key(id)
);

insert into customer(id, name, created_at)
values(10001, 'ACME', current_timestamp());

insert into customer(id, name, created_at)
values(10002, 'LOREM', current_timestamp());

insert into customer(id, name, created_at)
values(10003, 'IPSUM', current_timestamp());
