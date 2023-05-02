INSERT INTO customers (first_name, last_name, user_name, email) VALUES
('Richard', 'Feyman', 'user1', 'rf@gmail.com'),
('Paul', 'Dirac', 'user2', 'pd@gmail.com'),
('Max', 'Planck', 'user3', 'mp@gmail.com'),
('Stephen', 'Hawking', 'user4', 'sh@gmail.com'),
('James', 'Maxwell', 'user5', 'jm@gmail.com');

INSERT INTO products (name, description, price, stock) VALUES
('Keyboard', 'Mechanical keyboard', 15.50, 100),
('Mouse', 'Optical Mouse', 12.00, 100),
('Monitor', '4K Monitor', 350.00, 100),
('Headphones', 'Headphones', 25.99, 100),
('Sound system', '5.1 sound system', 225.50, 100);

INSERT INTO addresses (street_number, street_name, city, state, zip, customer_id) VALUES
(1337, 'N San Joaquin St', 'Stockton', 'CA', 95202, 1),
(478, 'Kings Rd', 'Brisbane', 'CA', 94005, 1),
(1100, 'S Belcher Rd', 'Largo', 'FL', 33771, 2),
(9, 'Crepe Myrtle St', 'Lake Placid', 'FL', 33852, 2),
(124, 'Platte St', 'Sterling', 'CO', 80751, 3),
(18933, 'E Carmel Dr', 'Aurora', 'CO', 80011, 3),
(1022, 'Irene Dr', 'Mesquite', 'TX', 75149, 4),
(1750, 'Ranchero Rd', 'Kerrville', 'TX', 78028, 4),
(15, 'Lalo Ln', 'Santa Fe', 'NM', 87505, 5),
(500, 'Paisano St NE #311', 'Albuquerque', 'NM', 87123, 5);

INSERT INTO payment_methods (payment_option) VALUES
('CREDIT_CARD'),
('DEBIT_CARD'),
('PAYPAL'),
('CRYPTO');

INSERT INTO customers_payment_methods (customer_id, payment_method_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(3, 1),
(3, 2),
(3, 4),
(4, 2),
(4, 4);