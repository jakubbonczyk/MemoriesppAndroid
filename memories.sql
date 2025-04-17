-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema memories
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `memories` DEFAULT CHARACTER SET utf8 ;
USE `memories` ;

-- -----------------------------------------------------
-- Table `memories`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`class` (
  `idclass` INT(11) NOT NULL,
  `class_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idclass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`users` (
  `idusers` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `role` ENUM('T', 'A', 'S') NOT NULL,
  `image` BLOB NULL,
  PRIMARY KEY (`idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`grades` (
  `idgrades` INT(11) NOT NULL,
  `grade` INT(11) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `users_idstudent` INT(11) NOT NULL,
  `users_idteacher` INT(11) NOT NULL,
  `class_idclass` INT(11) NOT NULL,
  PRIMARY KEY (`idgrades`, `users_idstudent`, `users_idteacher`, `class_idclass`),
  INDEX `fk_grates_users1_idx` (`users_idstudent` ASC),
  INDEX `fk_grates_users2_idx` (`users_idteacher` ASC),
  INDEX `fk_grates_class1_idx` (`class_idclass` ASC),
  CONSTRAINT `fk_grates_users1` FOREIGN KEY (`users_idstudent`) REFERENCES `memories`.`users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grates_users2` FOREIGN KEY (`users_idteacher`) REFERENCES `memories`.`users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grates_class1` FOREIGN KEY (`class_idclass`) REFERENCES `memories`.`class` (`idclass`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`groups`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`groups` (
  `idgroups` INT(11) NOT NULL,
  `group_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idgroups`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`schedule` (
  `idschedule` INT(11) NOT NULL,
  `lesson_date` DATE NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `class_idclass` INT(11) NOT NULL,
  `groups_idgroups` INT(11) NOT NULL,
  `users_idusers` INT(11) NOT NULL,
  PRIMARY KEY (`idschedule`, `class_idclass`, `groups_idgroups`, `users_idusers`),
  INDEX `fk_schedule_class1_idx` (`class_idclass` ASC),
  INDEX `fk_schedule_groups1_idx` (`groups_idgroups` ASC),
  INDEX `fk_schedule_users1_idx` (`users_idusers` ASC),
  CONSTRAINT `fk_schedule_class1` FOREIGN KEY (`class_idclass`) REFERENCES `memories`.`class` (`idclass`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_schedule_groups1` FOREIGN KEY (`groups_idgroups`) REFERENCES `memories`.`groups` (`idgroups`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_schedule_users1` FOREIGN KEY (`users_idusers`) REFERENCES `memories`.`users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`sensitive_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`sensitive_data` (
  `idsensitive_data` INT(11) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `users_idusers` INT(11) NOT NULL,
  PRIMARY KEY (`idsensitive_data`, `users_idusers`),
  INDEX `fk_sensitive_data_users_idx` (`users_idusers` ASC),
  CONSTRAINT `fk_sensitive_data_users` FOREIGN KEY (`users_idusers`) REFERENCES `memories`.`users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `memories`.`group_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `memories`.`group_members` (
  `idgroup_members` INT NOT NULL,
  `groups_idgroups` INT(11) NOT NULL,
  `users_idusers` INT(11) NOT NULL,
  PRIMARY KEY (`idgroup_members`, `groups_idgroups`, `users_idusers`),
  INDEX `fk_group_members_groups1_idx` (`groups_idgroups` ASC),
  INDEX `fk_group_members_users1_idx` (`users_idusers` ASC),
  CONSTRAINT `fk_group_members_groups1` FOREIGN KEY (`groups_idgroups`) REFERENCES `memories`.`groups` (`idgroups`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_members_users1` FOREIGN KEY (`users_idusers`) REFERENCES `memories`.`users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Dodanie trzech użytkowników
-- -----------------------------------------------------
INSERT INTO `users` (`idusers`, `name`, `surname`, `role`, `image`) VALUES
(1, 'Anna', 'Kowalska', 'S', NULL),
(2, 'Tomasz', 'Nowak', 'T', NULL),
(3, 'Barbara', 'Wiśniewska', 'A', NULL);

-- Hasła = test123 (BCrypt: $2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m)
INSERT INTO `sensitive_data` (`idsensitive_data`, `login`, `password`, `users_idusers`) VALUES
(1, 'student', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 1),
(2, 'teacher', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 2),
(3, 'admin', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 3);

-- Przywrócenie ustawień
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
