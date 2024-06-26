CREATE TABLE users(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name varchar(50),
    balance int
);

CREATE TABLE user_transaction(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    amount int,
    transaction_date timestamp,
    FOREIGN KEY (user_id) REFERENCES users(id) on delete cascade
);

INSERT INTO users (name,balance) values ('sam',3400),('mike',2200),('vijay',8800),('gopi',9000);