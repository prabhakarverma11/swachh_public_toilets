/*All User's gets stored in APP_USER table*/
create table APP_USER (
   id BIGINT NOT NULL AUTO_INCREMENT,
   sso_id VARCHAR(30) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (sso_id)
);

/*All Locations get stored in APP_LOCATION table*/
create table APP_LOCATION (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  address VARCHAR(200) NOT NULL,
  country VARCHAR(10) NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  image_URL VARCHAR(200) NOT NULL,
  type VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
);

create table APP_LOCATION_ULB_MAP (
  id BIGINT NOT NULL AUTO_INCREMENT,
  postal_code BIGINT NOT NULL,
  ulb_name VARCHAR(10) NOT NULL,
  location_id INT NOT NULL,
  PRIMARY KEY (id)
);

create table APP_PLACE_PHOTOS_MAP (
  id BIGINT NOT NULL AUTO_INCREMENT,
  place_id BIGINT NOT NULL,
  photo_url VARCHAR(500),
  PRIMARY KEY (id)
);

create table APP_PIN_CODE (
  id BIGINT NOT NULL AUTO_INCREMENT,
  location VARCHAR(150) NOT NULL,
  pin_code INT NOT NULL,
  state VARCHAR(150) NOT NULL,
  district VARCHAR(150) NOT NULL,
  PRIMARY KEY (id)
);


/* USER_PROFILE table contains all possible roles */
create table USER_PROFILE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(30) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
);

/* APP_PLACE table contains all possible placeIds */
CREATE TABLE `APP_PLACE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `place_id` varchar(100) NOT NULL,
  `location_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_PLACE_IDS_1_idx` (`location_id`),
  CONSTRAINT `fk_APP_PLACE_IDS_1` FOREIGN KEY (`location_id`) REFERENCES `APP_LOCATION` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

/* APP_PLACE_ID table contains all possible placeIds */
create table APP_PLACE_DETAIL(
  id BIGINT NOT NULL AUTO_INCREMENT,
  address VARCHAR(200) NOT NULL,
  name VARCHAR(100) NOT NULL,
  reference VARCHAR(250) NOT NULL,
  url VARCHAR(150) NOT NULL,
  rating DOUBLE,
  place_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  KEY `fk_APP_PLACE_IDS_1_idx` (`place_id`),
  CONSTRAINT FK_APP_PLACE_DETAIL FOREIGN KEY (place_id) REFERENCES APP_PLACE (place_id)
);

/* APP_PLACE_ID table contains all possible placeIds */
create table APP_REVIEW(
  id BIGINT NOT NULL AUTO_INCREMENT,
  author_name VARCHAR(50) NOT NULL,
  author_url VARCHAR(150) NOT NULL,
  profile_photo_url VARCHAR(150) NOT NULL,
  rating INT NOT NULL,
  time_of_review TIMESTAMP,
  text VARCHAR(250),
  place_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  KEY `fk_APP_PLACE_IDS_1_idx` (`place_id`),
  CONSTRAINT FK_APP_PLACE_DETAIL FOREIGN KEY (place_id) REFERENCES APP_PLACE (place_id)
);

/* JOIN TABLE for MANY-TO-MANY relationship*/
CREATE TABLE APP_USER_USER_PROFILE (
    user_id BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)
);

/* Populate USER_PROFILE Table */
INSERT INTO USER_PROFILE(type)
VALUES ('USER');

INSERT INTO USER_PROFILE(type)
VALUES ('ADMIN');

INSERT INTO USER_PROFILE(type)
VALUES ('DBA');


/* Populate one Admin User which will further create other users for the application using GUI */
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email)
VALUES ('sam','$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'Sam','Smith','samy@xyz.com');


/* Populate JOIN Table */
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)
  SELECT user.id, profile.id FROM APP_USER user, USER_PROFILE profile
  where user.sso_id='sam' and profile.type='ADMIN';

/* Create persistent_logins Table used to store rememberme related stuff*/
CREATE TABLE PERSISTENT_LOGINS (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);