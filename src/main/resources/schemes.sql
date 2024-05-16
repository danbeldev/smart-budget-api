CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id bigint generated always as identity,
    email varchar(48) unique not null,
    password_hash varchar(64) not null,

    CONSTRAINT PK__users__key PRIMARY KEY(id)
);

CREATE TABLE user_token_types
(
    id smallint generated always as identity,
    name varchar(24) unique not null,

    CONSTRAINT PK__user_token_types__key PRIMARY KEY(id)
);

INSERT INTO user_token_types (name) VALUES ('EMAIL_CHECK'), ('EMAIL_PASSWORD_RESET');

CREATE TABLE user_tokens
(
    user_id bigint,
    type_id smallint,
    token uuid not null default uuid_generate_v4(),
    expiration_time timestamp not null default current_date,

    CONSTRAINT PK__user_tokens__key PRIMARY KEY(user_id, type_id),
    CONSTRAINT FK__user_tokens__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK__user_tokens__type FOREIGN KEY(type_id) REFERENCES user_token_types(id)
);

CREATE TABLE goals
(
    id bigint generated always as identity,
    user_id bigint not null,
    name varchar(48) not null,
    target_amount decimal(10,2) not null,
    current_amount decimal(10, 2) not null,
    deadline date default null,
    is_achieved boolean not null default true,

    CONSTRAINT PK__goals__key PRIMARY KEY(id),
    CONSTRAINT FK__goals__user FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE expense_categories
(
    id int generated always as identity,
    name varchar(48) not null,
    user_id bigint default null,

    CONSTRAINT PK__expense_categories__key PRIMARY KEY(id),
    CONSTRAINT FK__expense_categories__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT UQ_expense_categories__name_user UNIQUE(name, user_id)
);

CREATE TABLE currencies
(
    id smallint generated always as identity,
    code varchar(3) unique not null,

    CONSTRAINT PK__currencies__key PRIMARY KEY(id)
);

INSERT INTO currencies(code) VALUES ('RUB'), ('CNY'), ('USD'), ('EUR');

CREATE TABLE transactions
(
    id bigint generated always as identity,
    user_id bigint not null,
    category_id int not null,
    currency_code_id smallint default null,
    amount_in_currency decimal(10, 2) default null,
    base_currency_code_id smallint not null default 1,
    amount_in_base_currency decimal(10, 2) not null,
    description varchar(128) default null,
    date date not null default current_date,

    CONSTRAINT PK__transactions__key PRIMARY KEY(id),
    CONSTRAINT FK__transactions__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK__transactions__category FOREIGN KEY(category_id) REFERENCES expense_categories(id),

    CONSTRAINT FK__transactions__currency_code FOREIGN KEY(currency_code_id) REFERENCES currencies(id),
    CONSTRAINT FK__transactions__base_currency_code FOREIGN KEY(base_currency_code_id) REFERENCES currencies(id)
);

CREATE TABLE budgets
(
    id bigint generated always as identity,
    user_id bigint not null,
    category_id int not null,
    amount_limit decimal(10, 2) not null,
    start_date date not null default current_date,
    end_date date default null,

    CONSTRAINT PK__budgets__key PRIMARY KEY(id),
    CONSTRAINT FK__budgets__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK__budgets__category FOREIGN KEY(category_id) REFERENCES expense_categories(id)
);

CREATE TABLE recurring_transaction_frequencies
(
    id  smallint generated always as identity,
    name varchar(24) unique not null,

    CONSTRAINT PK__recurring_transaction_frequencies__key PRIMARY KEY(id)
);

INSERT INTO recurring_transaction_frequencies(name) VALUES ('DAILY'), ('WEEKLY'), ('MONTHLY'), ('YEARLY');

CREATE TABLE recurring_transactions
(
    id bigint generated always as identity,
    user_id bigint not null,
    category_id int not null,
    frequency_id smallint not null,
    amount decimal(10, 2) not null,
    description varchar(48) not null,
    start_date date not null default current_date,

    CONSTRAINT PK__recurring_transactions__key PRIMARY KEY(id),
    CONSTRAINT FK__recurring_transactions__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK__recurring_transactions__category FOREIGN KEY(category_id) REFERENCES expense_categories(id),
    constraint FK__recurring_transactions__frequency FOREIGN KEY(frequency_id) REFERENCES recurring_transaction_frequencies(id)
);