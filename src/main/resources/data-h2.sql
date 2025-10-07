-- Directors
INSERT INTO director (name) VALUES ('Christopher Nolan'); -- id=1
INSERT INTO director (name) VALUES ('Hayao Miyazaki');    -- id=2

-- Actors
INSERT INTO actor (name) VALUES ('Leonardo DiCaprio');    -- id=1
INSERT INTO actor (name) VALUES ('Elliot Page');          -- id=2
INSERT INTO actor (name) VALUES ('Christian Bale');       -- id=3

-- Genres
INSERT INTO genre (name) VALUES ('Sci-Fi');               -- id=1
INSERT INTO genre (name) VALUES ('Thriller');             -- id=2
INSERT INTO genre (name) VALUES ('Animation');            -- id=3

-- Movies (NOTE: release_year, pas year)
INSERT INTO movie (title, release_year, duration_minutes, director_id)
VALUES ('Inception', 2010, 148, 1);                       -- id=1
INSERT INTO movie (title, release_year, duration_minutes, director_id)
VALUES ('Spirited Away', 2001, 125, 2);                   -- id=2

-- Liens Movie ↔ Actors
INSERT INTO movie_actor (movie_id, actor_id) VALUES (1, 1); -- Inception ↔ DiCaprio
INSERT INTO movie_actor (movie_id, actor_id) VALUES (1, 2); -- Inception ↔ Elliot Page

-- Liens Movie ↔ Genres
INSERT INTO movie_genre (movie_id, genre_id) VALUES (1, 1); -- Inception ↔ Sci-Fi
INSERT INTO movie_genre (movie_id, genre_id) VALUES (1, 2); -- Inception ↔ Thriller
INSERT INTO movie_genre (movie_id, genre_id) VALUES (2, 3); -- Spirited Away ↔ Animation
