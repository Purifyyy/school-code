-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáza: `glosar`
--

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `languages`
--

CREATE TABLE `languages` (
  `id` int UNSIGNED NOT NULL,
  `title` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `code` varchar(8) COLLATE utf8_slovak_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

--
-- Sťahujem dáta pre tabuľku `languages`
--

INSERT INTO `languages` (`id`, `title`, `code`) VALUES
(1, 'Slovenčina', 'sk'),
(3, 'English', 'en');

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `translations`
--

CREATE TABLE `translations` (
  `id` int UNSIGNED NOT NULL,
  `language_id` int UNSIGNED NOT NULL,
  `word_id` int UNSIGNED NOT NULL,
  `title` varchar(64) COLLATE utf8_slovak_ci NOT NULL,
  `description` text COLLATE utf8_slovak_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `words`
--

CREATE TABLE `words` (
  `id` int UNSIGNED NOT NULL,
  `title` varchar(64) COLLATE utf8_slovak_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_slovak_ci;

--
-- Kľúče pre exportované tabuľky
--

--
-- Indexy pre tabuľku `languages`
--
ALTER TABLE `languages`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pre tabuľku `translations`
--
ALTER TABLE `translations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `language_id` (`language_id`),
  ADD KEY `word_id` (`word_id`);

--
-- Indexy pre tabuľku `words`
--
ALTER TABLE `words`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pre exportované tabuľky
--

--
-- AUTO_INCREMENT pre tabuľku `languages`
--
ALTER TABLE `languages`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pre tabuľku `translations`
--
ALTER TABLE `translations`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;

--
-- AUTO_INCREMENT pre tabuľku `words`
--
ALTER TABLE `words`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- Obmedzenie pre exportované tabuľky
--

--
-- Obmedzenie pre tabuľku `translations`
--
ALTER TABLE `translations`
  ADD CONSTRAINT `translations_ibfk_1` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `translations_ibfk_2` FOREIGN KEY (`word_id`) REFERENCES `words` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
