\c lab_project dev1
CREATE TABLE accounts(
    id SERIAL PRIMARY KEY,
    email varchar(32) not null,
    password varchar(255) not null
);
insert into accounts (id, email, password) values (1, 'admin@mail.com', '$2a$10$Hqd3.moyT0uKB.jHEBGoJOag8l.nneI1t/IHyvEBy2jE4BxDRilaK');
-- username = admin, password = 123456
insert into accounts (id, email, password) values (2, 'user@mail.com', '$2a$10$Hqd3.moyT0uKB.jHEBGoJOag8l.nneI1t/IHyvEBy2jE4BxDRilaK');
-- username = user, password = 123456
commit;

CREATE TABLE roles(
    ID SERIAL PRIMARY KEY,
    account_id integer not null,
    role varchar(100) not null
);

insert into roles (account_id, role) values(1, 'ADMIN');
insert into roles (account_id, role) values(2, 'USER');
commit;