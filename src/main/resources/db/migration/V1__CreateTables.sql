CREATE TABLE customers (
  id BIGINT AUTO_INCREMENT NOT NULL,
   first_name VARCHAR(255) NULL,
   last_name VARCHAR(255) NULL,
   user_name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NULL,
   CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE payment_methods (
  id BIGINT AUTO_INCREMENT NOT NULL,
   payment_option VARCHAR(255) NULL,
   CONSTRAINT pk_payment_methods PRIMARY KEY (id)
);

CREATE TABLE customers_payment_methods (
  customer_id BIGINT NOT NULL,
   payment_method_id BIGINT NOT NULL,
   CONSTRAINT pk_customers_payment_methods PRIMARY KEY (customer_id, payment_method_id)
);

ALTER TABLE customers ADD CONSTRAINT uc_customers_username UNIQUE (user_name);

ALTER TABLE customers_payment_methods ADD CONSTRAINT fk_cuspaymet_on_customer FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE customers_payment_methods ADD CONSTRAINT fk_cuspaymet_on_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_methods (id);

CREATE TABLE products (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   price DOUBLE NULL,
   stock INT NULL,
   CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE addresses (
  id BIGINT AUTO_INCREMENT NOT NULL,
   customer_id BIGINT NOT NULL,
   street_number INT NULL,
   street_name VARCHAR(255) NULL,
   city VARCHAR(255) NULL,
   state VARCHAR(255) NULL,
   zip INT NULL,
   CONSTRAINT pk_addresses PRIMARY KEY (id)
);

ALTER TABLE addresses ADD CONSTRAINT FK_ADDRESSES_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT NOT NULL,
   customer_id BIGINT NOT NULL,
   address_id BIGINT NULL,
   status INT NULL,
   delivery_status INT NULL,
   payment_method_id BIGINT NULL,
   CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES addresses (id);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_PAYMENT_METHOD FOREIGN KEY (payment_method_id) REFERENCES payment_methods (id);


CREATE TABLE items (
  quantity INT NULL,
   product_id BIGINT NOT NULL,
   order_id BIGINT NOT NULL,
   CONSTRAINT pk_items PRIMARY KEY (product_id, order_id)
);

ALTER TABLE items ADD CONSTRAINT FK_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE items ADD CONSTRAINT FK_ITEMS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);


