SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Devoo
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `Devoo` ;
CREATE SCHEMA IF NOT EXISTS `Devoo` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `Devoo` ;

-- -----------------------------------------------------
-- Table `Devoo`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`users` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`users` (
  `username` VARCHAR(45) NOT NULL,
  `first-name` VARCHAR(45) NULL,
  `last-name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` MEDIUMTEXT NULL,
  `phone-number` MEDIUMTEXT NULL,
  `picture-url` MEDIUMTEXT NULL,
  `side-setting-preference` ENUM('LEFT','RIGHT') NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`rss-feeds`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`rss-feeds` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`rss-feeds` (
  `rss-name` VARCHAR(45) NOT NULL,
  `rss-url` MEDIUMTEXT NULL,
  PRIMARY KEY (`rss-name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`rss-users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`rss-users` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`rss-users` (
  `username` VARCHAR(45) NOT NULL,
  `rss-name` VARCHAR(45) NOT NULL,
  `rss-username` VARCHAR(45) NULL,
  `rss-password` VARCHAR(45) NULL,
  PRIMARY KEY (`username`, `rss-name`),
  INDEX `rss-name_idx` (`rss-name` ASC),
  CONSTRAINT `username-rss-users-fk`
    FOREIGN KEY (`username`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rss-name-fk`
    FOREIGN KEY (`rss-name`)
    REFERENCES `Devoo`.`rss-feeds` (`rss-name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`friends`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`friends` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`friends` (
  `username-1` VARCHAR(45) NOT NULL,
  `username-2` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username-1`, `username-2`),
  INDEX `username-friends-2-fk_idx` (`username-2` ASC),
  CONSTRAINT `username-friends-1-fk`
    FOREIGN KEY (`username-1`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `username-friends-2-fk`
    FOREIGN KEY (`username-2`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`activities` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`activities` (
  `id` INT NOT NULL,
  `name` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`user-activities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`user-activities` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`user-activities` (
  `username` VARCHAR(45) NOT NULL,
  `activity-id` INT NOT NULL,
  PRIMARY KEY (`username`, `activity-id`),
  INDEX `activity-fk_idx` (`activity-id` ASC),
  CONSTRAINT `activity--user-activities-fk`
    FOREIGN KEY (`activity-id`)
    REFERENCES `Devoo`.`activities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `username-user-activities-fk`
    FOREIGN KEY (`username`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`user-availability`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`user-availability` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`user-availability` (
  `username` VARCHAR(45) NOT NULL,
  `time-start` TIME NULL,
  `time-end` TIME NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `username-user-availability-fk`
    FOREIGN KEY (`username`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`conversations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`conversations` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`conversations` (
  `id` INT NOT NULL,
  `last-modified-timestamp` TIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`messages` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`messages` (
  `id` INT NOT NULL,
  `conversation-id` INT NULL,
  `time-sent` TIME NULL,
  `from-username` VARCHAR(45) NULL,
  `message` MEDIUMTEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `conversation-fk_idx` (`conversation-id` ASC),
  INDEX `username-messages-fk_idx` (`from-username` ASC),
  CONSTRAINT `conversation-messages-fk`
    FOREIGN KEY (`conversation-id`)
    REFERENCES `Devoo`.`conversations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `username-messages-fk`
    FOREIGN KEY (`from-username`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`messages-participant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`messages-participant` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`messages-participant` (
  `username` VARCHAR(45) NOT NULL,
  `conversation-id` INT NOT NULL,
  `time-read` TIME NULL,
  PRIMARY KEY (`username`, `conversation-id`),
  INDEX `conversations-messages-participant-fk_idx` (`conversation-id` ASC),
  CONSTRAINT `username-messages-participant-fk`
    FOREIGN KEY (`username`)
    REFERENCES `Devoo`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `conversations-messages-participant-fk`
    FOREIGN KEY (`conversation-id`)
    REFERENCES `Devoo`.`conversations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Devoo`.`data-points`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Devoo`.`data-points` ;

CREATE TABLE IF NOT EXISTS `Devoo`.`data-points` (
  `number-of-app-opens` INT NOT NULL,
  `number-of-matches` INT NULL,
  `number-of-invites` INT NULL,
  `number-of-installs` INT NULL,
  `locations` VARCHAR(45) NULL,
  `average-time-on-app` DOUBLE NULL,
  `activity-frequency` VARCHAR(45) NULL,
  `number-of-times-tom-morning-is-used` INT NULL,
  `percent-sucessful-match-rate` DOUBLE NULL,
  `number-chats-started` INT NULL,
  PRIMARY KEY (`number-of-app-opens`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
