CREATE TABLE rooms (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  capacity int NOT NULL,
  status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
  PRIMARY KEY (id)
);
