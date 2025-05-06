-- --------------------------------------------------------
-- MySQL/MariaDB DDL for database `memories` (from scratch)
-- Renamed table `groups` → `user_group`
-- All ID columns are AUTO_INCREMENT
-- --------------------------------------------------------

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- --------------------------------------------------------
-- Tabela `class`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `idclass`     INT(11)        NOT NULL AUTO_INCREMENT,
  `class_name`  VARCHAR(255)   NOT NULL,
  PRIMARY KEY (`idclass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- (przykładowe dane)
INSERT INTO `class` (`class_name`) VALUES
  ('Geografia 1');

-- --------------------------------------------------------
-- Tabela `users`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `idusers`     INT(11)            NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255)       NOT NULL,
  `surname`     VARCHAR(255)       NOT NULL,
  `role`        ENUM('T','A','S')  NOT NULL,
  `image`       BLOB               DEFAULT NULL,
  PRIMARY KEY (`idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- (przykładowe dane)
INSERT INTO `users` (`name`, `surname`, `role`, `image`) VALUES
  ('Anna', 'Kowalska',  'S', NULL),
  ('Tomasz','Nowak',    'T', NULL),
  ('Barbara','Wiśniewska','A',NULL);

-- --------------------------------------------------------
-- Tabela `sensitive_data`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `sensitive_data`;
CREATE TABLE `sensitive_data` (
  `idsensitive_data`  INT(11)     NOT NULL AUTO_INCREMENT,
  `login`             VARCHAR(255) NOT NULL,
  `password`          VARCHAR(256) NOT NULL,
  `users_idusers`     INT(11)      NOT NULL,
  PRIMARY KEY (`idsensitive_data`),
  KEY `fk_sensitive_data_users_idx` (`users_idusers`),
  CONSTRAINT `fk_sensitive_data_users`
    FOREIGN KEY (`users_idusers`) REFERENCES `users`(`idusers`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- (przykładowe dane)
INSERT INTO `sensitive_data` (`login`, `password`, `users_idusers`) VALUES
  ('student', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 1),
  ('teacher', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 2),
  ('admin',   '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 3);

-- --------------------------------------------------------
-- Tabela `user_group` (dawniej `groups`)
-- --------------------------------------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id`         INT(11)      NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- (przykładowe dane)
INSERT INTO `user_group` (`group_name`) VALUES
  ('Klasa 1');

-- --------------------------------------------------------
-- Tabela `group_members`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `group_members`;
CREATE TABLE `group_members` (
  `idgroup_members`  INT(11)   NOT NULL AUTO_INCREMENT,
  `user_group_id`    INT(11)   NOT NULL,
  `users_idusers`    INT(11)   NOT NULL,
  PRIMARY KEY (`idgroup_members`),
  KEY `fk_group_members_user_group_idx` (`user_group_id`),
  KEY `fk_group_members_users_idx`      (`users_idusers`),
  CONSTRAINT `fk_group_members_user_group`
    FOREIGN KEY (`user_group_id`) REFERENCES `user_group`(`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_members_users`
    FOREIGN KEY (`users_idusers`) REFERENCES `users`(`idusers`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- (przykładowe dane)
INSERT INTO `group_members` (`user_group_id`,`users_idusers`) VALUES
  (1,1);


DROP TABLE IF EXISTS `group_members_has_class`;

CREATE TABLE `group_members_has_class` (
                                           `id`                         INT(11)        NOT NULL AUTO_INCREMENT,
                                           `group_members_idgroup_members` INT(11)     NOT NULL,
                                           `class_idclass`              INT(11)        NOT NULL,
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `ux_gmc_group_member_class`
                                               (`group_members_idgroup_members`,`class_idclass`),
                                           KEY `fk_gmc_group_member_idx`    (`group_members_idgroup_members`),
                                           KEY `fk_gmc_class_idx`           (`class_idclass`),
                                           CONSTRAINT `fk_gmc_group_member`
                                               FOREIGN KEY (`group_members_idgroup_members`)
                                                   REFERENCES `group_members` (`idgroup_members`)
                                                   ON DELETE CASCADE ON UPDATE CASCADE,
                                           CONSTRAINT `fk_gmc_class`
                                               FOREIGN KEY (`class_idclass`)
                                                   REFERENCES `class` (`idclass`)
                                                   ON DELETE NO ACTION  ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabela `schedule`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
                            `idschedule`                    INT(11)      NOT NULL AUTO_INCREMENT,
                            `lesson_date`                   DATE         NOT NULL,
                            `start_time`                    TIME         NOT NULL,
                            `end_time`                      TIME         NOT NULL,
                            `group_members_has_class_id`    INT(11)      NOT NULL,
                            PRIMARY KEY (`idschedule`),
                            INDEX `idx_schedule_gmhc` (`group_members_has_class_id`),
                            CONSTRAINT `fk_schedule_gmhc`
                                FOREIGN KEY (`group_members_has_class_id`)
                                    REFERENCES `group_members_has_class` (`id`)
                                    ON DELETE NO ACTION
                                    ON UPDATE NO ACTION
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------
-- Tabela `grades`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `grades`;
CREATE TABLE `grades` (
  `idgrades`         INT(11)     NOT NULL AUTO_INCREMENT,
  `grade`            INT(11)     NOT NULL,
  `description`      VARCHAR(255),
  `users_idstudent`  INT(11)     NOT NULL,
  `users_idteacher`  INT(11)     NOT NULL,
  `class_idclass`    INT(11)     NOT NULL,
  PRIMARY KEY (`idgrades`),
  KEY `fk_grades_student_idx` (`users_idstudent`),
  KEY `fk_grades_teacher_idx` (`users_idteacher`),
  KEY `fk_grades_class_idx`   (`class_idclass`),
  CONSTRAINT `fk_grades_student`
    FOREIGN KEY (`users_idstudent`) REFERENCES `users`(`idusers`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grades_teacher`
    FOREIGN KEY (`users_idteacher`) REFERENCES `users`(`idusers`)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_grades_class`
    FOREIGN KEY (`class_idclass`) REFERENCES `class`(`idclass`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE `schedule` ADD COLUMN `generated` BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE grades ADD COLUMN type VARCHAR(50)  DEFAULT 'Sprawdzian';

INSERT INTO `grades` (`grade`,`description`,`users_idstudent`,`users_idteacher`,`class_idclass`) VALUES
  (3,'test',1,2,1);

COMMIT;
