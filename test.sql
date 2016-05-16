CREATE DATABASE ReflectionJdbcTestDB;

USE ReflectionJdbcTestDB;

CREATE TABLE Movies (
	ID int(10) unsigned NOT NULL,
	title varchar(200) DEFAULT NULL,
	date datetime NOT NULL,
	description varchar(200) NULL,
    PRIMARY KEY (ID, title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;