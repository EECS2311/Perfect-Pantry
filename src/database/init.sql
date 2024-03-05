INSERT INTO container (container_name) 
VALUES
('MainPantry'),
('Cereal Pantry'),
('MiniFridge');

INSERT INTO item (name, container, quantity, expiry, fg, fresh)
VALUES
('GoudaCheese','MainPantry',12, '2024-05-12', 'Dairy', 'Fresh'),
('FruitLoops', 'MainPantry', 1, '2024-08-1', 'Grain', 'Fresh'),
('JollyRanchersBox', 'MainPantry', 2, '2024-4-14', 'Fruit', 'Fresh');

-- Tips obtained from https://cookieandkate.com/food-storage-tips/
INSERT INTO storage_tips(name, info)
VALUES
('Apple', 'Apples will keep for a week at room temperature, or several weeks in the fruit drawer.'),
('Avocado', 'Store underripe avocado at room temperature until sufficiently ripened. To speed up this process, place it in a paper bag with an apple or banana (they contain ethylene gas, which will encourage ripening). Once the avocados are ripe (they yield slightly to a gentle squeeze), store them in the refrigerator to slow excess ripening.'),
('Banana', 'Store underripe bananas at room temperature until sufficiently ripened'),
('Beets', 'If they came home with nice greens attached, remove the greens and cook them up in some olive oil ASAP. The greens decay far faster than the attached vegetables. Remove any rubber bands and store these veggies (without the greens) in the vegetable drawer of your refrigerator. Or, they’ll keep for a few days at room temperature if you’re limited on storage.'),
('Celery', 'Celery is best stored in the bag it came in, with a couple of small air holes, in the refrigerator’s vegetable drawer. (Remove any rubber bands around the celery before storing.) If it goes limp, cut it into a few sections and soak it in some ice water to try to revive it.'),
('Radish', 'If they came home with nice greens attached, remove the greens and cook them up in some olive oil ASAP. The greens decay far faster than the attached vegetables. Remove any rubber bands and store these veggies (without the greens) in the vegetable drawer of your refrigerator. Or, they’ll keep for a few days at room temperature if you’re limited on storage.'),
('Ginger', 'Store ginger in the refrigerator’s vegetable drawer. It will keep for a week or two.')
('Milk', 'Store it in the fridge, of course, unless you bought a shelf-stable option (refrigerate after opening). Always give your milk a whiff before using—if it smells off, it’s gone bad.')
('Eggs', 'Store eggs in their original cartons inside the fridge—not in the refrigerator door. Wondering if your eggs are still fresh? Fill a bowl with cold water and place your eggs (a couple at a time). If they sink and lay flat on their sides (very fresh) or stand on one end at the bottom of the bowl (less fresh), they’re fine. If they float to the top, they’ve gone bad.');
