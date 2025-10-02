-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 10 mai 2023 à 16:05
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `stage`
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `conge`
--

INSERT INTO `conge` (`id_conge`, `matriculep`, `date_cng`, `date_debut`, `date_fin`, `duree`, `nom`, `prenom`, `rep_chef`, `rep_rh`, `solde_cng`, `statut`, `id_employe`, `id_type`) VALUES
(2, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 0, 'Blel', 'Blel', NULL, NULL, -2, 'mmmmm', NULL, 2),
(4, '0021', '2023-05-04', '2023-05-04', '2023-05-05', 0, 'Blel', 'Blel', NULL, NULL, -2, 'llll', NULL, 1),
(7, '0021', '2023-05-10', '2023-05-11', '2023-05-12', 1, 'Blel', 'Dhyaddine', 'O', 'O', -4, 'info12', NULL, 6),
(6, '0021', '2023-06-04', '2023-05-05', '2023-06-06', 1, 'Blel', 'Dhyaddine', 'O', 'N', -3, 'ppp', NULL, 4);

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

CREATE TABLE `personnel` (
  `id_employe` int(11) NOT NULL,
  `departement` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `matriculep` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `nom_responsable` varchar(255) DEFAULT NULL,
  `num_tel` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `poste` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `serv` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `personnel`
--

INSERT INTO `personnel` (`id_employe`, `departement`, `email`, `matriculep`, `nom`, `nom_responsable`, `num_tel`, `password`, `poste`, `prenom`, `serv`) VALUES
(1, NULL, 'test@gmail.com', 'String', 'String', NULL, 0, '123456', NULL, 'Blel', 1),
(2, 'kkk', 'test@gmail.com', '0002', 'string', NULL, 0, NULL, NULL, 'Blel', 1),
(3, NULL, 'testr@gmail.com', '0003', 'sss', NULL, 0, '$2a$10$3qWRwxPPWkO/QZT0zG1t2uK41s8QnBFE/YRjaP/DrncUW/.C9OprK', NULL, 'ppp', 1),
(4, NULL, 'testr@gmail.com', '0005', 'qqq', NULL, 0, '$2a$10$NbkYcjWDC7bvQHko9vLDkOODUU6JVapw7WVOxfephvft1F4MaW1Z6', NULL, 'Blel', 1),
(7, NULL, 'testr@gmail.com', '0007', 'String', NULL, 0, '$2a$10$tD9GQsbc/8K4aEHJZNR29OIrUlcaQ.eAGn.GZ7dd446nkq05O5cAq', NULL, 'Blel', 1),
(8, NULL, 'testr@gmail.com', '0009', 'String', NULL, 0, '$2a$10$3qWRwxPPWkO/QZT0zG1t2uK41s8QnBFE/YRjaP/DrncUW/.C9OprK', NULL, 'Blel', 2),
(9, NULL, 'testr@gmail.com', '0011', 'String', NULL, 0, '$2a$10$7/JGE6ZEMtvJ15.A6ZwwvOwsfmg5psFSwsnHbbnUHn7SOZWOP9bH.', NULL, 'Blel', 2),
(14, NULL, 'ff@gmail.com', '0021', 'Blel', NULL, 0, '$2a$10$3qWRwxPPWkO/QZT0zG1t2uK41s8QnBFE/YRjaP/DrncUW/.C9OprK', NULL, 'Dhyaddine', 1);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `nom_role` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`id`, `nom_role`) VALUES
(1, 'ROLE_CHEF'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_PERSONNEL');

-- --------------------------------------------------------

--
-- Structure de la table `sanction`
--

CREATE TABLE `sanction` (
  `id_sanction` bigint(20) NOT NULL,
  `date_sanction` datetime DEFAULT NULL,
  `matriculep` varchar(255) DEFAULT NULL,
  `nom_snaction` varchar(255) DEFAULT NULL,
  `id_employe` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `sanction`
--

INSERT INTO `sanction` (`id_sanction`, `date_sanction`, `matriculep`, `nom_snaction`, `id_employe`) VALUES
(1, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(2, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(3, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(4, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(5, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(6, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(7, '2023-05-04 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(8, '2023-05-10 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1),
(9, '2023-05-10 00:00:00', '0021', 'Vous avez depassez ton solde conges', 1);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

CREATE TABLE `service` (
  `id_service` bigint(20) NOT NULL,
  `cod_serv` varchar(255) DEFAULT NULL,
  `lib_serv` varchar(255) DEFAULT NULL,
  `matricule_chef` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`id_service`, `cod_serv`, `lib_serv`, `matricule_chef`) VALUES
(1, '01', 'service commerciald', '0001'),
(2, '02', 'département marketing', '0002'),
(5, '03', 'Hotellerie', '0001');

-- --------------------------------------------------------

--
-- Structure de la table `type_conge`
--

CREATE TABLE `type_conge` (
  `id_type` bigint(20) NOT NULL,
  `nom_typeconge` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_conge`
--

INSERT INTO `type_conge` (`id_type`, `nom_typeconge`) VALUES
(1, 'Congé pour enfant maladee'),
(2, 'Congé de présence parentale'),
(3, 'Congé de proche aidant'),
(4, 'Congé de solidarité familiale'),
(6, 'Décès d\'un proche');

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(3, 1),
(9, 2),
(14, 3);

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
-- Index pour la table `personnel`
--
ALTER TABLE `personnel`
  ADD PRIMARY KEY (`id_employe`),
  ADD KEY `FK74m98prvjcx408qxgdgbuss31` (`serv`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `sanction`
--
ALTER TABLE `sanction`
  ADD PRIMARY KEY (`id_sanction`),
  ADD KEY `FK777t0e4l85pfx21uy65x7m4ve` (`id_employe`);

--
-- Index pour la table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id_service`);

--
-- Index pour la table `type_conge`
--
ALTER TABLE `type_conge`
  ADD PRIMARY KEY (`id_type`);

--
-- Index pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `conge`
--
ALTER TABLE `conge`
  MODIFY `id_conge` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `personnel`
--
ALTER TABLE `personnel`
  MODIFY `id_employe` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `sanction`
--
ALTER TABLE `sanction`
  MODIFY `id_sanction` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `service`
--
ALTER TABLE `service`
  MODIFY `id_service` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `type_conge`
--
ALTER TABLE `type_conge`
  MODIFY `id_type` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
