\c lab_project dev1
CREATE TABLE accounts(
    id SERIAL PRIMARY KEY,
    email varchar(32) not null,
    password varchar(255) not null,
    role varchar(100) not null
);
insert into accounts (id, email, password, role) values (1, 'admin@mail.com', '$2a$10$Hqd3.moyT0uKB.jHEBGoJOag8l.nneI1t/IHyvEBy2jE4BxDRilaK', 'ADMIN');
-- username = admin, password = 123456
insert into accounts (id, email, password, role) values (2, 'user@mail.com', '$2a$10$Hqd3.moyT0uKB.jHEBGoJOag8l.nneI1t/IHyvEBy2jE4BxDRilaK', 'USER');
-- username = user, password = 123456
commit;

