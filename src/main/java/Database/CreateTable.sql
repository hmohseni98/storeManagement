CREATE TABLE IF NOT EXISTS admin
(
    id serial primary key,
    username char(30),
    password char(30),
    national_code varchar(10)
);
CREATE TABLE IF NOT EXISTS customer(
    id serial primary key ,
    username char(30),
    password char(30),
    address char(50)
    );
CREATE TABLE IF NOT EXISTS category(
    id serial primary key ,
    title char(100),
    description char(200),
    category_id int,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
    );
CREATE TABLE IF NOT EXISTS product(
    id serial primary key ,
    name char(50),
    description char(200),
    category_id int,
    gty int,
    price int,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
    );
CREATE TABLE IF NOT EXISTS shopping_card(
    id serial primary key ,
    data date,
    payed boolean
    );
CREATE TABLE IF NOT EXISTS "order"(
    id serial primary key ,
    product_id int,
    customer_id int,
    shopping_card_id int,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_shopping_card FOREIGN KEY (shopping_card_id) REFERENCES shopping_card(id)
    );

