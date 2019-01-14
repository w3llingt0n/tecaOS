-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 14-Jan-2019 às 20:14
-- Versão do servidor: 10.1.37-MariaDB
-- versão do PHP: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tecaos`
--
CREATE DATABASE IF NOT EXISTS `tecaos` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `tecaos`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `rg` varchar(255) NOT NULL,
  `data_nascimento` datetime NOT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` VALUES
(1, 'a', 'a', 'a', '2019-01-02 00:00:00', 'b'),
(2, 'Wellington', '123', '123', '1998-07-11 00:00:00', 'a'),
(3, 'Kaio', '321', '321', '2000-11-06 00:00:00', 'c');

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolucao`
--

CREATE TABLE `devolucao` (
  `iddevolucao` int(11) NOT NULL,
  `data_devolucao` datetime NOT NULL,
  `emprestimo_idemprestimo` int(11) NOT NULL,
  `emprestimo_cliente_id` int(11) NOT NULL,
  `emprestimo_livro_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `devolucao`
--

INSERT INTO `devolucao` VALUES
(1, '2019-01-14 00:00:00', 1, 1, 1),
(2, '2019-01-14 00:00:00', 3, 2, 5);

-- --------------------------------------------------------

--
-- Estrutura da tabela `emprestimo`
--

CREATE TABLE `emprestimo` (
  `idemprestimo` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `livro_id` int(11) NOT NULL,
  `data_emprestimo` datetime NOT NULL,
  `data_prevista_devolucao` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `emprestimo`
--

INSERT INTO `emprestimo` VALUES
(1, 1, 1, '2019-01-07 00:00:00', '2019-02-07 00:00:00'),
(2, 1, 1, '2019-01-07 00:00:00', '2019-02-07 00:00:00'),
(3, 2, 5, '2019-01-07 00:00:00', '2019-02-07 00:00:00'),
(4, 3, 3, '2019-01-14 00:00:00', '2019-02-13 00:00:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `livro`
--

CREATE TABLE `livro` (
  `id` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `categoria` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `ano_lancamento` varchar(4) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `livro`
--

INSERT INTO `livro` VALUES
(1, 'Admirável mundo novo', 'A', 'Aldous Leonard Huxley', '2014', 10),
(2, 'Fahrenheit451', 'A', 'Ray Bradbury e Cid Knipet', '2012', 5),
(3, 'As Crônicas de Nárnia ', 'B', 'C. S. Lewis', '2009', 12),
(4, 'Medo Clássico', 'C', 'Edgar Allan Poe e Marcia Heloisa', '2017', 3),
(5, 'A Longa Viagem a Um Pequeno Planeta Hostil', 'D', 'Becky Chambers e Raquel Mortiz', '2017', 6);

-- --------------------------------------------------------

--
-- Estrutura da tabela `multa`
--

CREATE TABLE `multa` (
  `idmulta` int(11) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `pago` tinyint(1) NOT NULL DEFAULT '0',
  `cliente_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `multa`
--

INSERT INTO `multa` VALUES
(1, '20.10', 0, 1),
(2, '0.00', 0, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cpf_UNIQUE` (`cpf`);

--
-- Indexes for table `devolucao`
--
ALTER TABLE `devolucao`
  ADD PRIMARY KEY (`iddevolucao`,`emprestimo_idemprestimo`,`emprestimo_cliente_id`,`emprestimo_livro_id`),
  ADD KEY `fk_devolucao_emprestimo1_idx` (`emprestimo_idemprestimo`,`emprestimo_cliente_id`,`emprestimo_livro_id`);

--
-- Indexes for table `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD PRIMARY KEY (`idemprestimo`,`cliente_id`,`livro_id`),
  ADD KEY `fk_cliente_has_livro_livro1_idx` (`livro_id`),
  ADD KEY `fk_cliente_has_livro_cliente_idx` (`cliente_id`);

--
-- Indexes for table `livro`
--
ALTER TABLE `livro`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `multa`
--
ALTER TABLE `multa`
  ADD PRIMARY KEY (`idmulta`,`cliente_id`),
  ADD KEY `fk_multa_cliente1_idx` (`cliente_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `devolucao`
--
ALTER TABLE `devolucao`
  MODIFY `iddevolucao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `emprestimo`
--
ALTER TABLE `emprestimo`
  MODIFY `idemprestimo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `livro`
--
ALTER TABLE `livro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `multa`
--
ALTER TABLE `multa`
  MODIFY `idmulta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `devolucao`
--
ALTER TABLE `devolucao`
  ADD CONSTRAINT `fk_devolucao_emprestimo1` FOREIGN KEY (`emprestimo_idemprestimo`,`emprestimo_cliente_id`,`emprestimo_livro_id`) REFERENCES `emprestimo` (`idemprestimo`, `cliente_id`, `livro_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD CONSTRAINT `fk_cliente_has_livro_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cliente_has_livro_livro1` FOREIGN KEY (`livro_id`) REFERENCES `livro` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `multa`
--
ALTER TABLE `multa`
  ADD CONSTRAINT `fk_multa_cliente1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
