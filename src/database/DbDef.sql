CREATE TYPE Food_Group AS ENUM ('Grain', 'Protein', 'Vegetable', 'Fruit', 'Dairy');
CREATE TYPE Freshness AS ENUM ('Expired', 'Fresh', 'Near_Expiry');


CREATE TABLE container (
    container_name varchar(50) PRIMARY KEY
);

CREATE TABLE item(
    name varchar(50),
    container varchar(50) REFERENCES container(container_name) ON DELETE CASCADE,
    quantity INTEGER, 
    expiry Date,
    fg Food_Group,
    fresh Freshness,
    PRIMARY KEY(name, container)
);

create Table storage_tips(
    name varchar(50),
    info TEXT,
    PRIMARY KEY(name)
);

create Table general_tips(
    info TEXT,
);

