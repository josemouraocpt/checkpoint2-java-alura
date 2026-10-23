CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL UNIQUE,
  token varchar(64) DEFAULT NULL,
  expiration_token timestamp NULL DEFAULT NULL,
  role enum('ADMIN','USER') NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);
