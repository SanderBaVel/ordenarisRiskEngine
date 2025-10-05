-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: ordenaris_riskengine
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `datos_contables`
--

DROP TABLE IF EXISTS `datos_contables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datos_contables` (
  `id_contable` bigint NOT NULL AUTO_INCREMENT,
  `id_empresa` bigint NOT NULL,
  `ventas_anuales` double DEFAULT NULL,
  `deuda_activa` double DEFAULT NULL,
  `flujo_efectivo` double DEFAULT NULL,
  `fecha_actualizacion` date NOT NULL,
  PRIMARY KEY (`id_contable`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `datos_contables_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datos_contables`
--

LOCK TABLES `datos_contables` WRITE;
/*!40000 ALTER TABLE `datos_contables` DISABLE KEYS */;
INSERT INTO `datos_contables` VALUES (1,1,12000000,0,2500000,'2025-09-15'),(2,2,480000,0,100000,'2025-09-15'),(3,3,2400000,0,600000,'2025-09-15'),(4,4,3600000,0,800000,'2025-09-15'),(5,5,3000000,500000,400000,'2025-09-15'),(6,6,9000000,0,1500000,'2025-09-15');
/*!40000 ALTER TABLE `datos_contables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pagos`
--

DROP TABLE IF EXISTS `detalle_pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pagos` (
  `id_pago_detalle` bigint NOT NULL AUTO_INCREMENT,
  `id_empresa` bigint NOT NULL,
  `monto` decimal(38,2) DEFAULT NULL,
  `fecha_vencimiento` date NOT NULL,
  `fecha_pago` date DEFAULT NULL,
  `refinanciado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_pago_detalle`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `detalle_pagos_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pagos`
--

LOCK TABLES `detalle_pagos` WRITE;
/*!40000 ALTER TABLE `detalle_pagos` DISABLE KEYS */;
INSERT INTO `detalle_pagos` VALUES (1,1,15000.00,'2024-10-01','2024-10-01',0),(2,1,15000.00,'2024-11-01','2024-11-01',0),(3,1,15000.00,'2024-12-01','2024-12-01',0),(4,1,15000.00,'2025-01-01','2025-01-01',0),(5,1,15000.00,'2025-02-01','2025-02-01',0),(6,1,15000.00,'2025-03-01','2025-03-01',0),(7,1,15000.00,'2025-04-01','2025-04-01',0),(8,1,15000.00,'2025-05-01','2025-05-01',0),(9,1,15000.00,'2025-06-01','2025-06-01',0),(10,1,15000.00,'2025-07-01','2025-07-01',0),(11,1,15000.00,'2025-08-01','2025-08-01',0),(12,1,15000.00,'2025-09-01','2025-09-01',0),(13,1,15000.00,'2024-10-01','2024-10-01',0),(14,1,15000.00,'2024-11-01','2024-11-01',0),(15,1,15000.00,'2024-12-01','2024-12-01',0),(16,1,15000.00,'2025-01-01','2025-01-01',0),(17,1,15000.00,'2025-02-01','2025-02-01',0),(18,1,15000.00,'2025-03-01','2025-03-01',0),(19,1,15000.00,'2025-04-01','2025-04-01',0),(20,1,15000.00,'2025-05-01','2025-05-01',0),(21,1,15000.00,'2025-06-01','2025-06-01',0),(22,1,15000.00,'2025-07-01','2025-07-01',0),(23,1,15000.00,'2025-08-01','2025-08-01',0),(24,1,15000.00,'2025-09-01','2025-09-01',0),(25,3,12000.00,'2025-06-01','2025-06-10',0),(26,4,20000.00,'2025-07-01','2025-07-01',0),(27,5,25000.00,'2025-05-01',NULL,0),(28,6,30000.00,'2025-08-01','2025-08-01',0);
/*!40000 ALTER TABLE `detalle_pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empresa` (
  `id_empresa` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `rfc` varchar(255) DEFAULT NULL,
  `fecha_registro` date NOT NULL,
  PRIMARY KEY (`id_empresa`),
  UNIQUE KEY `rfc` (`rfc`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (1,'TechNova S.A.','TNV010101AA1','2023-01-15'),(2,'StartGrow S. de R.L.','SGR020202BB2','2025-03-01'),(3,'FinTrust S.A.','FTR030303CC3','2020-06-10'),(4,'LegalRisk S.A.','LRS040404DD4','2022-05-20'),(5,'MaxCredit S.A.','MCS050505EE5','2021-08-12'),(6,'AutoLease S.A.','ALS060606FF6','2022-02-11');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verificacion_legal`
--

DROP TABLE IF EXISTS `verificacion_legal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verificacion_legal` (
  `id_verificacion` bigint NOT NULL AUTO_INCREMENT,
  `id_empresa` bigint NOT NULL,
  `tiene_demandas` tinyint(1) DEFAULT '0',
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_verificacion` date NOT NULL,
  PRIMARY KEY (`id_verificacion`),
  KEY `id_empresa` (`id_empresa`),
  CONSTRAINT `verificacion_legal_ibfk_1` FOREIGN KEY (`id_empresa`) REFERENCES `empresa` (`id_empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verificacion_legal`
--

LOCK TABLES `verificacion_legal` WRITE;
/*!40000 ALTER TABLE `verificacion_legal` DISABLE KEYS */;
INSERT INTO `verificacion_legal` VALUES (1,1,0,'Sin registros legales','2025-09-15'),(2,2,0,'Sin registros legales','2025-09-15'),(3,3,0,'Sin registros legales','2025-09-15'),(4,4,1,'Juicio mercantil abierto con proveedor externo','2025-09-15'),(5,5,0,'Sin registros legales','2025-09-15'),(6,6,0,'Sin registros legales','2025-09-15');
/*!40000 ALTER TABLE `verificacion_legal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-05 10:04:02
