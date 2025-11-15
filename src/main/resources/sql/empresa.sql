-- --------------------------------------------------------
-- Host:                         54.167.13.129
-- Versión del servidor:         9.5.0 - MySQL Community Server - GPL
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para empresa
CREATE DATABASE IF NOT EXISTS `empresa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `empresa`;

-- Volcando estructura para procedimiento empresa.datos_dep
DELIMITER //
CREATE PROCEDURE `datos_dep`(codigo INT, OUT nombre_departamento VARCHAR(15), OUT localidad_departamento VARCHAR(15))
BEGIN
SET localidad_departamento='INEXISTENTE';
SET nombre_departamento='INEXISTENTE';
SELECT dnombre, loc INTO nombre_departamento, localidad_departamento 
FROM departamentos
WHERE dept_no=codigo;
END//
DELIMITER ;

-- Volcando estructura para tabla empresa.departamentos
CREATE TABLE IF NOT EXISTS `departamentos` (
  `dept_no` tinyint NOT NULL,
  `dnombre` varchar(15) DEFAULT NULL,
  `loc` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`dept_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla empresa.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `emp_no` smallint unsigned NOT NULL,
  `apellido` varchar(10) DEFAULT NULL,
  `oficio` varchar(10) DEFAULT NULL,
  `dir` smallint DEFAULT NULL,
  `fecha_alt` date DEFAULT NULL,
  `salario` float(6,2) DEFAULT NULL,
  `comision` float(6,2) DEFAULT NULL,
  `dept_no` tinyint NOT NULL,
  PRIMARY KEY (`emp_no`),
  KEY `dept_no` (`dept_no`),
  CONSTRAINT `empleados_ibfk_1` FOREIGN KEY (`dept_no`) REFERENCES `departamentos` (`dept_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para procedimiento empresa.emple_depar
DELIMITER //
CREATE PROCEDURE `emple_depar`(IN dep INT, OUT cuenta INT)
BEGIN
SELECT * FROM empleados
WHERE dept_no=dep;

SELECT COUNT(*) INTO cuenta
FROM empleados
WHERE dept_no=dep;
END//
DELIMITER ;

-- Volcando estructura para función empresa.nombre_dep
DELIMITER //
CREATE FUNCTION `nombre_dep`(codigo INT) RETURNS varchar(15) CHARSET utf8mb4
    DETERMINISTIC
BEGIN
DECLARE nombre VARCHAR(15);
SET nombre='INEXISTENTE';
SELECT dnombre INTO nombre
FROM departamentos
WHERE dept_no=codigo;
RETURN nombre;
END//
DELIMITER ;

-- Volcando estructura para procedimiento empresa.subida_salario
DELIMITER //
CREATE PROCEDURE `subida_salario`(dep INT, subida INT)
BEGIN
UPDATE empleados SET salario=salario + (salario*subida/100)
WHERE dept_no=dep;
END//
DELIMITER ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
