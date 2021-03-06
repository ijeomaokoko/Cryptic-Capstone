SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cryptic_chat_test
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cryptic_chat_test` ;

-- -----------------------------------------------------
-- Schema cryptic_chat_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cryptic_chat_test` DEFAULT CHARACTER SET utf8 ;
USE `cryptic_chat_test` ;

-- -----------------------------------------------------
-- Table `chat_app_test`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat_test`.`user` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat_test`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(250) NOT NULL,
  `password_hash` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat_test`.`room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat_test`.`room` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat_test`.`room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`room_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chat_app_test`.`room_has_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat_test`.`room_has_user` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat_test`.`room_has_user` (
  `room_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`room_id`, `user_id`),
  INDEX `fk_room_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_room_has_user_room_idx` (`room_id` ASC) VISIBLE,
  CONSTRAINT `fk_room_has_user_room`
    FOREIGN KEY (`room_id`)
    REFERENCES `cryptic_chat_test`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cryptic_chat_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cryptic_chat_test`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cryptic_chat_test`.`message` ;

CREATE TABLE IF NOT EXISTS `cryptic_chat_test`.`message` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `message` VARCHAR(250) NOT NULL,
  `time_stamp` TIMESTAMP(1) NOT NULL,
  `room_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`message_id`),
  INDEX `fk_message_room1_idx` (`room_id` ASC) VISIBLE,
  INDEX `fk_message_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `cryptic_chat_test`.`room` (`room_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cryptic_chat_test`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

delimiter //
create procedure set_known_good_state()
begin

    delete from message;
    alter table message auto_increment = 1;

	insert into message
		(message, time_stamp, room_id, user_id)
	values
		('Hello World!','04/23/17 04:34:22',1,1),
		('Test 1','10/03/2017 07:29:46',2,2),
		('Test 2','03/21/2023 23:35:56',1,2),
		('Test 3','09/13/2033 01:25:46',2,1);

end //
-- 4. Change the statement terminator back to the original.
delimiter ;