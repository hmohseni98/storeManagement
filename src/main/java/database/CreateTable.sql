CREATE TABLE IF NOT EXISTS admin
(
    id serial primary key,
    username varchar(30),
    password varchar(30),
    national_code char(10)
);
CREATE TABLE IF NOT EXISTS customer(
    id serial primary key ,
    username varchar(30),
    password varchar(30),
    address varchar(100)
    );
CREATE TABLE IF NOT EXISTS category(
    id serial primary key ,
    title varchar(100),
    description varchar(200),
    category_id int
    );
CREATE TABLE IF NOT EXISTS product(
    id serial primary key ,
    name varchar(50),
    description varchar(200),
    category_id int,
    qty int,
    price int,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
    );
CREATE TABLE IF NOT EXISTS shoppingcard(
    id serial primary key ,
    date date,
    payed boolean
    );
CREATE TABLE IF NOT EXISTS orders(
    id serial primary key ,
    product_id int,
    customer_id int,
    shoppingcard_id int,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_shopping_card FOREIGN KEY (shoppingcard_id) REFERENCES shoppingcard(id)
    );