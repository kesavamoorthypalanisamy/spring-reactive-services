CREATE TABLE users(
    id serial PRIMARY KEY,
    name varchar(50),
    balance int
);

CREATE TABLE user_transaction(
    id serial PRIMARY KEY,
    user_id BIGINT,
    amount int,
    transaction_date timestamp,
    constraint fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade
);

INSERT INTO users (name,balance) values ('sam',1000),('mike',1200),('vijay',800),('gopi',2000);