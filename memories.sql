-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2025 at 10:00 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `memories`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `class`
--

CREATE TABLE `class` (
  `idclass` int(11) NOT NULL,
  `class_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `class`
--

INSERT INTO `class` (`idclass`, `class_name`) VALUES
(1, 'Klasa 1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `grades`
--

CREATE TABLE `grades` (
  `idgrades` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `users_idstudent` int(11) NOT NULL,
  `users_idteacher` int(11) NOT NULL,
  `class_idclass` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `grades`
--

INSERT INTO `grades` (`idgrades`, `grade`, `description`, `users_idstudent`, `users_idteacher`, `class_idclass`) VALUES
(1, 3, 'test', 1, 2, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `groups`
--

CREATE TABLE `groups` (
  `idgroups` int(11) NOT NULL,
  `group_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`idgroups`, `group_name`) VALUES
(1, 'Klasa 1');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `group_members`
--

CREATE TABLE `group_members` (
  `idgroup_members` int(11) NOT NULL,
  `groups_idgroups` int(11) NOT NULL,
  `users_idusers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `group_members`
--

INSERT INTO `group_members` (`idgroup_members`, `groups_idgroups`, `users_idusers`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `schedule`
--

CREATE TABLE `schedule` (
  `idschedule` int(11) NOT NULL,
  `lesson_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `class_idclass` int(11) NOT NULL,
  `groups_idgroups` int(11) NOT NULL,
  `users_idusers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sensitive_data`
--

CREATE TABLE `sensitive_data` (
  `idsensitive_data` int(11) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(256) NOT NULL,
  `users_idusers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `sensitive_data`
--

INSERT INTO `sensitive_data` (`idsensitive_data`, `login`, `password`, `users_idusers`) VALUES
(1, 'student', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 1),
(2, 'teacher', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 2),
(3, 'admin', '$2a$10$mNl2xuzUAMsLGuxM9msvDeqEu3.DlcP0Rtz0oBmUEoBVhdId7n09m', 3);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `idusers` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `role` enum('T','A','S') NOT NULL,
  `image` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idusers`, `name`, `surname`, `role`, `image`) VALUES
(1, 'Anna', 'Kowalska', 'S', NULL),
(2, 'Tomasz', 'Nowak', 'T', NULL),
(3, 'Barbara', 'Wiśniewska', 'A', NULL);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`idclass`);

--
-- Indeksy dla tabeli `grades`
--
ALTER TABLE `grades`
  ADD PRIMARY KEY (`idgrades`,`users_idstudent`,`users_idteacher`,`class_idclass`),
  ADD KEY `fk_grates_users1_idx` (`users_idstudent`),
  ADD KEY `fk_grates_users2_idx` (`users_idteacher`),
  ADD KEY `fk_grates_class1_idx` (`class_idclass`);

--
-- Indeksy dla tabeli `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`idgroups`);

--
-- Indeksy dla tabeli `group_members`
--
ALTER TABLE `group_members`
  ADD PRIMARY KEY (`idgroup_members`,`groups_idgroups`,`users_idusers`),
  ADD KEY `fk_group_members_groups1_idx` (`groups_idgroups`),
  ADD KEY `fk_group_members_users1_idx` (`users_idusers`);

--
-- Indeksy dla tabeli `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`idschedule`,`class_idclass`,`groups_idgroups`,`users_idusers`),
  ADD KEY `fk_schedule_class1_idx` (`class_idclass`),
  ADD KEY `fk_schedule_groups1_idx` (`groups_idgroups`),
  ADD KEY `fk_schedule_users1_idx` (`users_idusers`);

--
-- Indeksy dla tabeli `sensitive_data`
--
ALTER TABLE `sensitive_data`
  ADD PRIMARY KEY (`idsensitive_data`,`users_idusers`),
  ADD KEY `fk_sensitive_data_users_idx` (`users_idusers`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idusers`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `grades`
--
ALTER TABLE `grades`
  MODIFY `idgrades` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `grades`
--
ALTER TABLE `grades`
  ADD CONSTRAINT `fk_grates_class1` FOREIGN KEY (`class_idclass`) REFERENCES `class` (`idclass`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_grates_users1` FOREIGN KEY (`users_idstudent`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_grates_users2` FOREIGN KEY (`users_idteacher`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `group_members`
--
ALTER TABLE `group_members`
  ADD CONSTRAINT `fk_group_members_groups1` FOREIGN KEY (`groups_idgroups`) REFERENCES `groups` (`idgroups`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_group_members_users1` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `fk_schedule_class1` FOREIGN KEY (`class_idclass`) REFERENCES `class` (`idclass`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_schedule_groups1` FOREIGN KEY (`groups_idgroups`) REFERENCES `groups` (`idgroups`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_schedule_users1` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `sensitive_data`
--
ALTER TABLE `sensitive_data`
  ADD CONSTRAINT `fk_sensitive_data_users` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
