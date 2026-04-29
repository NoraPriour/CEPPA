ALTER TABLE articles DROP COLUMN auteur;
ALTER TABLE articles ADD COLUMN auteur_id BIGINT REFERENCES users(id);

UPDATE articles SET auteur_id = 3 WHERE id = 1;
UPDATE articles SET auteur_id = 5 WHERE id = 2;
UPDATE articles SET auteur_id = 2 WHERE id = 3;
UPDATE articles SET auteur_id = 1 WHERE id = 4;