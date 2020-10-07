--liquibase formatted sql

--changeset str1ng:create-test-employee
--preconditions onFail:CONTINUE onError:CONTINUE
insert into employees (
    id,
    first_name,
    login,
    password,
    patronymic,
    salt,
    second_name
    )
values (
    0,
    'Даниил',
    'str1ng',
    '54636346df',
    'Александрович',
    'salt',
    'Середа'
);
--rollback delete from employees where login = 'str1ng';
