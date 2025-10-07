-- Nettoyage
DELETE FROM movie_actor;
DELETE FROM movie_genre;
DELETE FROM movie;
DELETE FROM actor;
DELETE FROM genre;
DELETE FROM director;

-- Semences
INSERT INTO director(name) VALUES ('Christopher Nolan');
INSERT INTO director(name) VALUES ('Hayao Miyazaki');

INSERT INTO actor(name) VALUES ('Leonardo DiCaprio');
INSERT INTO actor(name) VALUES ('Elliot Page');
INSERT INTO actor(name) VALUES ('Christian Bale');

INSERT INTO genre(name) VALUES ('Sci-Fi');
INSERT INTO genre(name) VALUES ('Thriller');
INSERT INTO genre(name) VALUES ('Animation');

-- Films
INSERT INTO movie(title, release_year, duration_minutes, director_id) VALUES ('Inception', 2010, 148,
                                                                              (SELECT id FROM director WHERE name='Christopher Nolan'));
INSERT INTO movie(title, release_year, duration_minutes, director_id) VALUES ('Interstellar', 2014, 169,
                                                                              (SELECT id FROM director WHERE name='Christopher Nolan'));
INSERT INTO movie(title, release_year, duration_minutes, director_id) VALUES ('Spirited Away', 2001, 125,
                                                                              (SELECT id FROM director WHERE name='Hayao Miyazaki'));

-- Liens acteurs
INSERT INTO movie_actor(movie_id, actor_id) VALUES (
                                                       (SELECT id FROM movie WHERE title='Inception'),
                                                       (SELECT id FROM actor WHERE name='Leonardo DiCaprio')
                                                   );
INSERT INTO movie_actor(movie_id, actor_id) VALUES (
                                                       (SELECT id FROM movie WHERE title='Inception'),
                                                       (SELECT id FROM actor WHERE name='Elliot Page')
                                                   );

-- Liens genres
INSERT INTO movie_genre(movie_id, genre_id) VALUES (
                                                       (SELECT id FROM movie WHERE title='Inception'),
                                                       (SELECT id FROM genre WHERE name='Sci-Fi')
                                                   );
INSERT INTO movie_genre(movie_id, genre_id) VALUES (
                                                       (SELECT id FROM movie WHERE title='Inception'),
                                                       (SELECT id FROM genre WHERE name='Thriller')
                                                   );
