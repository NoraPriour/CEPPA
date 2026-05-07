ALTER TABLE articles DROP COLUMN author;
ALTER TABLE articles ADD COLUMN author_id BIGINT REFERENCES users(id) ON DELETE SET NULL;

UPDATE articles SET author_id = 3 WHERE id = 1;
UPDATE articles SET author_id = 5 WHERE id = 2;
UPDATE articles SET author_id = 2 WHERE id = 3;
UPDATE articles SET author_id = 1 WHERE id = 4;