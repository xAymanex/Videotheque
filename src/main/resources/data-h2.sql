-- src/test/resources/truncate.sql
DELETE FROM movie_actor;
DELETE FROM movie;
DELETE FROM actor;
DELETE FROM director;
DELETE FROM genre;

-- ===== Référentiels : idempotents via MERGE =====
MERGE INTO director (name) KEY(name) VALUES ('Christopher Nolan');
MERGE INTO director (name) KEY(name) VALUES ('Hayao Miyazaki');

MERGE INTO actor (name) KEY(name) VALUES ('Leonardo DiCaprio');
MERGE INTO actor (name) KEY(name) VALUES ('Joseph Gordon-Levitt');
MERGE INTO actor (name) KEY(name) VALUES ('Elliot Page');
MERGE INTO actor (name) KEY(name) VALUES ('Christian Bale');
MERGE INTO actor (name) KEY(name) VALUES ('Heath Ledger');
MERGE INTO actor (name) KEY(name) VALUES ('Matthew McConaughey');
MERGE INTO actor (name) KEY(name) VALUES ('Anne Hathaway');
MERGE INTO actor (name) KEY(name) VALUES ('Rumi Hiiragi');
MERGE INTO actor (name) KEY(name) VALUES ('Miyu Irino');

MERGE INTO genre (name) KEY(name) VALUES ('Sci-Fi');
MERGE INTO genre (name) KEY(name) VALUES ('Thriller');
MERGE INTO genre (name) KEY(name) VALUES ('Animation');
MERGE INTO genre (name) KEY(name) VALUES ('Action');
MERGE INTO genre (name) KEY(name) VALUES ('Drama');
MERGE INTO genre (name) KEY(name) VALUES ('Adventure');

-- ===== Films : INSERT ... SELECT avec JOINs pour fournir director_id ET genre_id =====

-- Inception (Sci-Fi, Nolan)
INSERT INTO movie (title, release_year, duration_minutes, director_id, genre_id)
SELECT 'Inception', 2010, 148, d.id, g.id
FROM director d
         JOIN genre g ON g.name = 'Sci-Fi'
WHERE d.name = 'Christopher Nolan'
  AND NOT EXISTS (SELECT 1 FROM movie m WHERE m.title = 'Inception');

-- The Dark Knight (Action, Nolan)
INSERT INTO movie (title, release_year, duration_minutes, director_id, genre_id)
SELECT 'The Dark Knight', 2008, 152, d.id, g.id
FROM director d
         JOIN genre g ON g.name = 'Action'
WHERE d.name = 'Christopher Nolan'
  AND NOT EXISTS (SELECT 1 FROM movie m WHERE m.title = 'The Dark Knight');

-- Interstellar (Sci-Fi, Nolan)
INSERT INTO movie (title, release_year, duration_minutes, director_id, genre_id)
SELECT 'Interstellar', 2014, 169, d.id, g.id
FROM director d
         JOIN genre g ON g.name = 'Sci-Fi'
WHERE d.name = 'Christopher Nolan'
  AND NOT EXISTS (SELECT 1 FROM movie m WHERE m.title = 'Interstellar');

-- Spirited Away (Animation, Miyazaki)
INSERT INTO movie (title, release_year, duration_minutes, director_id, genre_id)
SELECT 'Spirited Away', 2001, 125, d.id, g.id
FROM director d
         JOIN genre g ON g.name = 'Animation'
WHERE d.name = 'Hayao Miyazaki'
  AND NOT EXISTS (SELECT 1 FROM movie m WHERE m.title = 'Spirited Away');

-- ===== Casting (movie_actor) : idempotent via NOT EXISTS =====

-- Inception cast
INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Inception' AND a.name = 'Leonardo DiCaprio'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Inception' AND a.name = 'Joseph Gordon-Levitt'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Inception' AND a.name = 'Elliot Page'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

-- The Dark Knight cast
INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'The Dark Knight' AND a.name = 'Christian Bale'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'The Dark Knight' AND a.name = 'Heath Ledger'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

-- Interstellar cast
INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Interstellar' AND a.name = 'Matthew McConaughey'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Interstellar' AND a.name = 'Anne Hathaway'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

-- Spirited Away cast
INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Spirited Away' AND a.name = 'Rumi Hiiragi'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);

INSERT INTO movie_actor (movie_id, actor_id)
SELECT m.id, a.id
FROM movie m, actor a
WHERE m.title = 'Spirited Away' AND a.name = 'Miyu Irino'
  AND NOT EXISTS (SELECT 1 FROM movie_actor ma WHERE ma.movie_id = m.id AND ma.actor_id = a.id);
