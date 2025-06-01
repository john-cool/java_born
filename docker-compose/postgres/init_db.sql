CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id         uuid PRIMARY KEY,
    surname    varchar(255) NOT NULL,
    name       varchar(255) NOT NULL,
    patronymic varchar(255),
    email      varchar(255) NOT NULL,
    login      varchar(255) unique NOT NULL,
    password   varchar(255) NOT NULL
);

CREATE TABLE transaction_categories
(
    code varchar(10)  NOT NULL UNIQUE,
    name varchar(255) NOT NULL
);

CREATE TABLE account_types
(
    code varchar(10)  NOT NULL UNIQUE,
    name varchar(255) NOT NULL
);

CREATE TABLE transactions
(
    id            uuid PRIMARY KEY,
    category_code varchar(255) NOT NULL REFERENCES transaction_categories (code),
    date          date         NOT NULL,
    sum           numeric      NOT NULL,
    account_id    uuid         NOT NULL,
    type          varchar(10)  NOT NULL
);

CREATE TABLE accounts
(
    id        uuid PRIMARY KEY,
    type_code varchar(10) NOT NULL REFERENCES account_types (code),
    balance   numeric,
    user_id   uuid REFERENCES users (id)
);

INSERT INTO users (id, surname, name, patronymic, email, login, password)
VALUES ('64cf7809-cb49-47cf-9c71-ea012a9f0268', 'Пушкин', 'Александр', 'Сергеевич', 'pushkinas@mail.ru', 'pushkinas', 12345),
       ('0af20fb6-b1f9-4e23-9290-7d138d9c3f54', 'Иванов', 'Иван', 'Иванович', 'ivanovii@mail.ru', 'ivanovii', 54321),
       ('a70fbfc5-546a-4723-a885-b9bd1e666a89', 'Сидоров', 'Сидор', 'Сидорович', 'sidorovss@mail.ru', 'sidorovss', 6549);

INSERT INTO transaction_categories (code, name)
VALUES ('cafe', 'Кафе'),
       ('restaurant', 'Ресторан'),
       ('salary', 'Заработная плата'),
       ('health', 'Здоровье'),
       ('automobile', 'Автомобиль');

INSERT INTO account_types (code, name)
VALUES ('personal', 'Личный'),
       ('credit', 'Кредитный');

INSERT INTO transactions (id, category_code, date, sum, account_id, type)
VALUES ('db49769c-d492-4947-976f-5f107370d6ce', 'cafe', '2025-04-23', 1000, 'c15e3646-96f3-4b6f-8058-347ed6c8c115',
        'withdrawal'),
       ('16c810ce-d4da-4712-8a00-c4098ba2413b', 'restaurant', '2025-04-21', 2300,
        'c15e3646-96f3-4b6f-8058-347ed6c8c115', 'withdrawal'),
       ('841b2fc0-1218-4a05-8464-557ee6b64f4c', 'health', '2025-04-20', 500, 'b1767aeb-3da6-4b07-a20c-ba220c937186',
        'withdrawal'),
       ('46d2403e-cba9-4a7f-9de2-3a746ce44e86', 'salary', '2025-01-14', 11000, '56fc42b3-4ff3-4c1f-b39f-c9a81ae7b474',
        'deposit'),
       ('061b1078-bc59-4f21-915b-344f6d9020c8', 'automobile', '2025-02-07', 4500,
        '56fc42b3-4ff3-4c1f-b39f-c9a81ae7b474', 'withdrawal'),
       ('4788253f-fe09-4c2b-83e6-2b86ac145fc4', 'health', '2025-03-06', 860, '673c0dbd-9cd2-44a9-b6da-140fd56bbbab',
        'withdrawal'),
       ('f7895574-7b63-4ca9-aabe-243d3425f94e', 'automobile', '2025-02-22', 15000,
        'f765253d-f562-4f6f-ad94-6b79eda1076e', 'withdrawal'),
       ('a21c84d9-5f7d-46a4-8310-bace6fa6120f', 'salary', '2025-01-01', 15000, '90fbeaf4-c9cb-4789-b332-a6516abafdfe',
        'deposit');

INSERT INTO accounts (id, type_code, balance, user_id)
VALUES ('c15e3646-96f3-4b6f-8058-347ed6c8c115', 'personal', 10000, '64cf7809-cb49-47cf-9c71-ea012a9f0268'),
       ('b1767aeb-3da6-4b07-a20c-ba220c937186', 'credit', 250000, '64cf7809-cb49-47cf-9c71-ea012a9f0268'),
       ('56fc42b3-4ff3-4c1f-b39f-c9a81ae7b474', 'personal', 10000, '0af20fb6-b1f9-4e23-9290-7d138d9c3f54'),
       ('673c0dbd-9cd2-44a9-b6da-140fd56bbbab', 'credit', 250000, '0af20fb6-b1f9-4e23-9290-7d138d9c3f54'),
       ('f765253d-f562-4f6f-ad94-6b79eda1076e', 'personal', 10000, 'a70fbfc5-546a-4723-a885-b9bd1e666a89'),
       ('90fbeaf4-c9cb-4789-b332-a6516abafdfe', 'credit', 250000, 'a70fbfc5-546a-4723-a885-b9bd1e666a89');

