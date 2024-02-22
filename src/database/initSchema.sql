CREATE TYPE Food_Group AS ENUM ('Grain', 'Protein', 'Vegetable', 'Fruit', 'Dairy');
CREATE TYPE Freshness AS ENUM ('Expired', 'Fresh', 'Near_Expiry');

CREATE TABLE item(
    name varchar(50) PRIMARY KEY,
    container varchar(50) REFERENCES container(container_name),
    quantity INTEGER, 
    expiry Date,
    fg Food_Group,
    fresh Freshness
);

CREATE TABLE container (
    container_name varchar(50) PRIMARY KEY
);

INSERT INTO container (container_name) 
VALUES
('MainPantry'),
('Cereal Pantry'),
('MiniFridge');

INSERT INTO item (name, container, quantity, expiry, fg, fresh)
VALUES
('GoudaCheese','MainPantry',12, 2024-05-12, 'Dairy', 'Fresh'),
('FruitLoops', 'MainPantry', 1, 2024-08-1, 'Grain', 'Fresh'),
('JollyRanchersBox', 'MainPantry', 2, 2024-0-9, 'Fruit', 'Fresh');
