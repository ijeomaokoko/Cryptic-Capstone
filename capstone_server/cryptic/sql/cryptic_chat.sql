-- -----------------------------------------------------
-- Schema cryptic_chat
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cryptic_chat` ;

-- -----------------------------------------------------
-- Schema cryptic_chat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptic_chat` DEFAULT CHARACTER SET utf8 ;
USE `cryptic_chat`;

-- -----------------------------------------------------
-- Table `cryptic_chat`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat`.`user` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`user` (
  user_id int primary key auto_increment,
  username VARCHAR(250) NOT NULL,
  user_password VARCHAR(250) NOT NULL
 )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat`.`room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat`.`room` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`room` (
  room_id int primary key auto_increment,
  room_name VARCHAR(250) NOT NULL
  )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat`.`room_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat`.`room_has_user` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`room_has_user` (
  room_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (room_id, user_id),
  INDEX fk_room_has_user_user1_idx (user_id ASC) VISIBLE,
  INDEX fk_room_has_user_room_idx (room_id ASC) VISIBLE,
  CONSTRAINT fk_room_has_user_room
    FOREIGN KEY (room_id)
    REFERENCES cryptic_chat.room (room_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_room_has_user_user1
    FOREIGN KEY (user_id)
    REFERENCES cryptic_chat.user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat`.`message` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`message` (
  message_id INT NOT NULL AUTO_INCREMENT,
  message VARCHAR(250) NOT NULL,
  `timestamp` TIMESTAMP(6) NOT NULL,
  room_id INT NOT NULL,
  user_id INT NOT NULL,
  username VARCHAR(250) NOT NULL,
  PRIMARY KEY (message_id),
  INDEX fk_message_room1_idx (room_id ASC) VISIBLE,
  INDEX fk_message_user1_idx (user_id ASC) VISIBLE,
  CONSTRAINT fk_message_room1
    FOREIGN KEY (room_id)
    REFERENCES cryptic_chat.room (room_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_message_user1
    FOREIGN KEY (user_id)
    REFERENCES cryptic_chat.user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



DROP TABLE IF EXISTS `cryptic_chat`.`role` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`role` (
  role_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(250) NOT NULL,
  PRIMARY KEY (role_id))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat`.`role_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat`.`role_has_user` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`role_has_user` (
  role_id INT NOT NULL,
  user_id INT NOT NULL,
  PRIMARY KEY (role_id, user_id),
  INDEX fk_role_has_user_user1_idx (user_id ASC) VISIBLE,
  INDEX fk_role_has_user_role1_idx (role_id ASC) VISIBLE,
  CONSTRAINT fk_role_has_user_role1
    FOREIGN KEY (role_id)
    REFERENCES cryptic_chat.role (role_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_role_has_user_user1
    FOREIGN KEY (user_id)
    REFERENCES cryptic_chat.user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
