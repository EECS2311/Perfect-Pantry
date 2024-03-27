
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
    info TEXT
);

CREATE TABLE grocery(
    name VARCHAR(50) PRIMARY KEY UNIQUE
);

CREATE TABLE recipes (
     id INTEGER PRIMARY KEY,
     title VARCHAR(255) NOT NULL,
     image_url TEXT
);

CREATE TABLE ingredients (
     id INTEGER PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     unit VARCHAR(50),
     image_url TEXT,
     original TEXT
);

CREATE TABLE recipe_ingredients (
    recipe_id INTEGER REFERENCES recipes(id) ON DELETE CASCADE,
    ingredient_id INTEGER REFERENCES ingredients(id) ON DELETE CASCADE,
    amount DECIMAL,
    is_used BOOLEAN, -- TRUE if the ingredient is used, FALSE if it is missed
    PRIMARY KEY (recipe_id, ingredient_id)
);

CREATE TABLE detailed_instructions (
   recipe_id INTEGER REFERENCES recipes(id) ON DELETE CASCADE,
   step_number INTEGER NOT NULL,
   instruction TEXT NOT NULL,
   PRIMARY KEY (recipe_id, step_number)
);