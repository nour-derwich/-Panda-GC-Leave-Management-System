-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 04 mai 2023 à 20:39
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pfe`
--

-- --------------------------------------------------------

--
-- Structure de la table `conge`
--

CREATE TABLE `conge` (
  `id_conge` bigint(20) NOT NULL,
  `matriculep` varchar(255) DEFAULT NULL,
  `date_cng` date DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `duree` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `rep_chef` varchar(255) DEFAULT NULL,
  `rep_rh` varchar(255) DEFAULT NULL,
  `solde_cng` bigint(20) DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `id_employe` int(11) DEFAULT NULL,
  `id_type` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `conge`
--

INSERT INTO `conge` (`id_conge`, `matriculep`, `date_cng`, `date_debut`, `date_fin`, `duree`, `nom`, `prenom`, `rep_chef`, `rep_rh`, `solde_cng`, `statut`, `id_employe`, `id_type`) VALUES
(1, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 2, NULL, NULL, 'O', 'O', -2, 'oooo', NULL, 1),
(2, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 0, NULL, NULL, NULL, NULL, -2, 'mmmmm', NULL, 2),
(3, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 0, NULL, NULL, NULL, NULL, -2, 'lll', NULL, NULL),
(4, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 0, NULL, NULL, NULL, NULL, -2, 'llll', NULL, 1),
(5, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 1, 'ff@gmail.com', '$2a$10$3qWRwxPPWkO/QZT0zG1t2uK41s8QnBFE/YRjaP/DrncUW/.C9OprK', 'N', NULL, -3, 'qqq', NULL, 2),
(6, '0021', '2023-06-04', '2023-05-05', '2023-06-06', 1, 'Blel', 'Dhyaddine', NULL, NULL, -3, 'ppp', NULL, 4);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `conge`
--
ALTER TABLE `conge`
  ADD PRIMARY KEY (`id_conge`),
  ADD KEY `FKm02cgykuu1eq8u00iqymoombi` (`id_employe`),
  ADD KEY `FKddf6vgyjsc7jijylckoqc5adr` (`id_type`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `conge`
--
ALTER TABLE `conge`
  MODIFY `id_conge` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
