--liquibase formatted sql

--changeset str1ng:create-test-employees-and-rights

--------------Заполнение таблицы Rights--------------
insert into rights (
    id,
    name
    )
values (
    0,
    'Администратор'
);
--rollback delete from employees where name = 'Администратор';

insert into rights (
    id,
    name
    )
values (
    1,
    'Пользователь'
);
--rollback delete from employees where name = 'Пользователь';

--------------Заполнение таблицы Employees--------------
insert into employees (
    id,
    first_name,
    second_name,
    patronymic,
    login,
    password,
    salt,
    rights_id
    )
values (
    0,
    'Даниил',
    'Середа',
    'Александрович',
    'user',
    '$2a$10$UFmVgxviFarFcWCiFBn3GOV4wo6McVlnfFyd2WdB6849vbbk1fAMS',
    'cf75ac23f76646',
    1
);
--rollback delete from employees where login = 'user';

insert into employees (
    id,
    first_name,
    second_name,
    patronymic,
    login,
    password,
    salt,
    rights_id
    )
values (
    1,
    'Михаил',
    'Титов',
    'Викторович',
    'admin',
    '$2a$10$OywQ4uDF/L0Or/CcuDCexun8t74USZ70exzX7aGTPyy5PKbYOI/T.',
    '396da01d028a40',
    0
);
--rollback delete from employees where login = 'admin';


