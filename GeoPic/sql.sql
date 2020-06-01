
-- three tables
-- users:login table
--  photinfo:used to store photos
-- faceinfo:used to store faces

DROP TABLE IF EXISTS users;
create table users(
	user_id serial primary key,
	username varchar not null unique,
	password varchar not null,
	avatar varchar,
	createtime timestamp);

DROP TABLE IF EXISTS photoinfo;
create table photoinfo(
	photo_id serial primary key,
	takenplace varchar,
	takentime timestamp,
	geo Geometry(('POINT'),3857),
	photolables text[],
	photopath varchar not null,
	facesid integer[],
	user_id integer,
	foreign key(user_id)references users(user_id));

DROP TABLE IF EXISTS faceinfo ;
create table faceinfo(
	face_id serial primary key,
	facelables text[],
	faceToken text[] not null,
	facepath varchar not null
	);
