CREATE USER dev1 with password '123456';
CREATE DATABASE lab_project;
GRANT ALL PRIVILEGES ON DATABASE lab_project to dev1;
\c lab_project postgres
GRANT ALL ON SCHEMA public TO dev1;

