drop table if exists films;
drop table if exists users;
drop table if exists friends;
drop table if exists genres;
drop table if exists mpas;
drop table if exists likes;
drop table if exists film_genres;

create table films (
id integer auto_increment primary key,
name varchar,
description varchar,
release_date date,
duration integer,
genre_id integer,
rating_id integer
);

create table users (
id integer auto_increment primary key,
email varchar,
login varchar,
name varchar,
birthday date
);

create table friends (
id integer auto_increment primary key,
user_id integer,
friend_id integer,
status varchar
);

create table genres (
id integer auto_increment primary key,
name varchar
);

create table mpas (
id integer auto_increment primary key,
name varchar
);

create table likes (
id integer auto_increment primary key,
user_id integer,
film_id integer
);

create table film_genres (
id integer auto_increment primary key,
genre_id integer,
film_id integer
);

