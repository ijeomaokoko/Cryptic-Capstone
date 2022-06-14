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
  password_hash VARCHAR(250) NOT NULL,
  disabled bit not null default(0)
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

use cryptic_chat;
DROP TABLE IF EXISTS `crytpic_chat`.`token` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat`.`token` (
  `token` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`token`))
ENGINE = InnoDB;

delimiter //
create procedure set_known_good_state()
begin

	delete from room_has_user;
	delete from role_has_user;
    
    delete from message;
    alter table message auto_increment = 1;
    
    delete from room_has_user;
    
	delete from `user`;
    alter table `user` auto_increment = 1;
    delete from `role`;
    alter table `role` auto_increment = 1;
    
    delete from room;
    alter table room auto_increment = 1;
    
    insert into role(`name`) values("USER");
    insert into role(`name`) values("ADMIN");
    
    -- passwords are set to "P@ssw0rd!"
    insert into `user` (username, password_hash, disabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 0);

    insert into role_has_user(role_id, user_id) values(2, 1);
    insert into role_has_user(role_id, user_id) values(2, 2);
    
    insert into room(room_id, room_name) values(1, "Main");
    insert into room(room_id, room_name) values(2, "test room 2");
    insert into room(room_id, room_name) values(3, "test room 3");
    insert into room(room_id, room_name) values(4, "test room 4");
    
    insert into room_has_user(room_id, user_id) values(1, 1);
	insert into room_has_user(room_id, user_id) values(2, 1);
    
    insert into message(message, `timestamp`, room_id, user_id, username) values("test", "2022-05-03 12:12:12", 1, 1, "john@smith.com");
    insert into message(message, `timestamp`, room_id, user_id, username) values("test2", "2022-05-03 11:12:12", 1, 1, "john@smith.com");
    insert into message(message, `timestamp`, room_id, user_id, username) values("test3", "2022-05-03 10:12:12", 1, 1, "john@smith.com");
    
end //
delimiter ;

use cryptic_chat;

