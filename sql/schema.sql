create table if not exists employees
(
    id       int auto_increment
        primary key,
    username varchar(50)  not null,
    password varchar(255) not null,
    role     varchar(50)  not null,
    constraint username
        unique (username)
);

create table if not exists products
(
    id       int auto_increment
        primary key,
    name     varchar(255)   not null,
    category varchar(255)   null,
    quantity int default 0  null,
    price    decimal(10, 2) null
);

