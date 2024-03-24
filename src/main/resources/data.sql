INSERT INTO Member (username, password)
VALUES ('john_doe', '$2a$10$9A6KpkGrpzdTRA9yGgtxP.65uu/vHo7ABvJ859iGPun0H8B2s0eFe');
INSERT INTO Member (username, password)
VALUES ('jane_smith', '$2a$10$9A6KpkGrpzdTRA9yGgtxP.65uu/vHo7ABvJ859iGPun0H8B2s0eFe');
INSERT INTO Member (username, password)
VALUES ('admin', '$2a$10$9A6KpkGrpzdTRA9yGgtxP.65uu/vHo7ABvJ859iGPun0H8B2s0eFe');
-- password = password

INSERT INTO Recipe (name, ingredients, author_id)
VALUES ('Spaghetti Carbonara', 'Spaghetti, Bacon, Eggs, Parmesan Cheese',
        (SELECT id FROM Member WHERE username = 'john_doe'));
INSERT INTO Recipe (name, ingredients, author_id)
VALUES ('Chicken Curry', 'Chicken, Curry Sauce, Rice', (SELECT id FROM Member WHERE username = 'jane_smith'));
INSERT INTO Recipe (name, ingredients, author_id)
VALUES ('Chocolate Cake', 'Flour, Sugar, Cocoa Powder, Eggs, Milk, Butter',
        (SELECT id FROM Member WHERE username = 'jane_smith'));
INSERT INTO Recipe (name, ingredients, author_id)
VALUES ('Caesar Salad', 'Romaine Lettuce, Croutons, Parmesan Cheese, Caesar Dressing',
        (SELECT id FROM Member WHERE username = 'jane_smith'));
INSERT INTO Recipe (name, ingredients, author_id)
VALUES ('Beef Stew', 'Beef, Carrots, Potatoes, Onion, Beef Broth',
        (SELECT id FROM Member WHERE username = 'jane_smith'));
