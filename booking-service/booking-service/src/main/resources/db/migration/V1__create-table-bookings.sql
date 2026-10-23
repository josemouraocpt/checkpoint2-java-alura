CREATE TABLE bookings (
  id bigint NOT NULL AUTO_INCREMENT,
  start_time datetime(6) NOT NULL,
  end_time datetime(6) NOT NULL,
  user bigint NOT NULL,
  room bigint NOT NULL,
  status ENUM('ACTIVE', 'CANCELLED') NOT NULL,
  PRIMARY KEY (id)
);
