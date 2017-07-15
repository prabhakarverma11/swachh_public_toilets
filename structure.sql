DROP DATABASE IF EXISTS `spt`;
CREATE DATABASE `spt`;
use `spt`;

--
-- Table structure for table `APP_LOCATION`
--

DROP TABLE IF EXISTS `APP_LOCATION`;

CREATE TABLE `APP_LOCATION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `address` varchar(200) NOT NULL,
  `country` varchar(10) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `image_URL` varchar(200) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13591 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_PLACE`
--

DROP TABLE IF EXISTS `APP_PLACE`;

CREATE TABLE `APP_PLACE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `place_id` varchar(100) NOT NULL,
  `location_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_PLACE_IDS_1_idx` (`location_id`),
  CONSTRAINT `fk_APP_PLACE_IDS_1` FOREIGN KEY (`location_id`) REFERENCES `APP_LOCATION` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5238 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_ADMIN_VERIFICATION`
--

DROP TABLE IF EXISTS `APP_ADMIN_VERIFICATION`;

CREATE TABLE `APP_ADMIN_VERIFICATION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `category` varchar(45) NOT NULL,
  `location_type` varchar(45) DEFAULT NULL,
  `location_id` bigint(20) NOT NULL,
  `ulb_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_ADMIN_VERIFICATION_1_idx` (`location_id`),
  CONSTRAINT `fk_APP_ADMIN_VERIFICATION_1` FOREIGN KEY (`location_id`) REFERENCES `APP_LOCATION` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_PIN_CODE_DETAIL`
--

DROP TABLE IF EXISTS `APP_PIN_CODE_DETAIL`;

CREATE TABLE `APP_PIN_CODE_DETAIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location` varchar(100) NOT NULL,
  `pin_code` int(11) NOT NULL,
  `state` varchar(100) NOT NULL,
  `district` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=920 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_PLACE_DETAIL`
--

DROP TABLE IF EXISTS `APP_PLACE_DETAIL`;

CREATE TABLE `APP_PLACE_DETAIL` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(200) NOT NULL,
  `name` varchar(100) NOT NULL,
  `reference` varchar(350) NOT NULL,
  `url` varchar(350) NOT NULL,
  `rating` int(11) NOT NULL,
  `place_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `place_id_UNIQUE` (`place_id`),
  CONSTRAINT `fk_APP_PLACE_DETAIL_1` FOREIGN KEY (`place_id`) REFERENCES `APP_PLACE` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5007 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_PLACE_PHOTO_MAP`
--

DROP TABLE IF EXISTS `APP_PLACE_PHOTO_MAP`;

CREATE TABLE `APP_PLACE_PHOTO_MAP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `place_id` bigint(20) NOT NULL,
  `photo_url` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_PLACE_PHOTOS_MAP_1_idx` (`place_id`),
  CONSTRAINT `fk_APP_PLACE_PHOTOS_MAP_1` FOREIGN KEY (`place_id`) REFERENCES `APP_PLACE` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_PLACE_ULB_MAP`
--

DROP TABLE IF EXISTS `APP_PLACE_ULB_MAP`;

CREATE TABLE `APP_PLACE_ULB_MAP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ulb_name` varchar(45) DEFAULT NULL,
  `place_id` bigint(20) NOT NULL,
  `postal_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_PLACE_ULB_MAP_1_idx` (`place_id`),
  CONSTRAINT `fk_APP_PLACE_ULB_MAP_1` FOREIGN KEY (`place_id`) REFERENCES `APP_PLACE` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5073 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_REVIEW`
--

DROP TABLE IF EXISTS `APP_REVIEW`;

CREATE TABLE `APP_REVIEW` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(50) NOT NULL,
  `author_url` varchar(250) DEFAULT NULL,
  `profile_photo_url` varchar(250) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `time_of_review` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(4050) DEFAULT NULL,
  `place_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_APP_REVIEW_1_idx` (`place_id`),
  CONSTRAINT `fk_APP_REVIEW_1` FOREIGN KEY (`place_id`) REFERENCES `APP_PLACE` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2178 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_ULB`
--

DROP TABLE IF EXISTS `APP_ULB`;

CREATE TABLE `APP_ULB` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `display_name` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_USER`
--

DROP TABLE IF EXISTS `APP_USER`;

CREATE TABLE `APP_USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Table structure for table `USER_PROFILE`
--

DROP TABLE IF EXISTS `USER_PROFILE`;

CREATE TABLE `USER_PROFILE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Table structure for table `APP_USER_USER_PROFILE`
--

DROP TABLE IF EXISTS `APP_USER_USER_PROFILE`;

CREATE TABLE `APP_USER_USER_PROFILE` (
  `user_id` bigint(20) NOT NULL,
  `user_profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_PROFILE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `APP_USER` (`id`),
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `USER_PROFILE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `PERSISTENT_LOGINS`
--

DROP TABLE IF EXISTS `PERSISTENT_LOGINS`;

CREATE TABLE `PERSISTENT_LOGINS` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;