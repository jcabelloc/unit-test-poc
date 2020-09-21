-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema 
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cliente` (
  `cod_cliente` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `tipo_documento` VARCHAR(4) NOT NULL,
  `nro_documento` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`cod_cliente`),
  UNIQUE INDEX `nroDocumento_UNIQUE` (`nro_documento` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1001;


-- CLIENTE

INSERT IGNORE INTO `cliente` (`cod_cliente`, `nombre`, `tipo_documento`, `nro_documento`) VALUES 
('1001', 'ABC TRANSPORTES', 'RUC', '20257758214'),
('1002', 'JUAN PEREZ', 'DNI', '57485362');



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- ----------------------------------------------------------------
-- CARGA INICIAL DE DATA
-- ----------------------------------------------------------------

