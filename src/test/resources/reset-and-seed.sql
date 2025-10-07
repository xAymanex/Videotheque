-- Nettoyage ordonné
DELETE FROM movie_actor;
DELETE FROM movie_genre;
DELETE FROM movie;
DELETE FROM actor;
DELETE FROM genre;
DELETE FROM director;

-- Seed minimal cohérent avec les tests
INSERT INTO director(name) VALUES ('Christopher Nolan');         -- id D1
INSERT INTO actor(name) VALUES ('Leonardo DiCaprio');            -- id A1
INSERT INTO genre(name) VALUES ('Sci-Fi');                       -- id G1

INSERT INTO movie(title, release_year, duration_minutes, director_id)
VALUES ('Inception', 2010, 148, (SELECT id FROM director WHERE name='Christopher Nolan'));
INSERT INTO movie(title, release_year, duration_minutes, director_id)
VALUES ('Interstellar', 2014, 169, (SELECT id FROM director WHERE name='Christopher Nolan'));

INSERT INTO movie_actor(movie_id, actor_id)
VALUES ((SELECT id FROM movie WHERE title='Inception'),
        (SELECT id FROM actor WHERE name='Leonardo DiCaprio'));
INSERT INTO movie_genre(movie_id, genre_id)
VALUES ((SELECT id FROM movie WHERE title='Inception'),
        (SELECT id FROM genre WHERE name='Sci-Fi'));
