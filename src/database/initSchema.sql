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

CREATE TABLE container(
    container_name varchar(50) PRIMARY KEY
);
