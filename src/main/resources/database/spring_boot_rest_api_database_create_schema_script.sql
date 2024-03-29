-- MySQL Script generated by MySQL Workbench
-- Tue Mar  5 16:07:08 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema product_reviews_app
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `product_reviews_app` ;

-- -----------------------------------------------------
-- Schema product_reviews_app
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `product_reviews_app` DEFAULT CHARACTER SET utf8 ;
USE `product_reviews_app` ;

-- -----------------------------------------------------
-- Table `product_reviews_app`.`brand`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_reviews_app`.`brand` ;

CREATE TABLE IF NOT EXISTS `product_reviews_app`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_reviews_app`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_reviews_app`.`product` ;

CREATE TABLE IF NOT EXISTS `product_reviews_app`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `brand_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_brand1_idx` (`brand_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_brand1`
    FOREIGN KEY (`brand_id`)
    REFERENCES `product_reviews_app`.`brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `product_reviews_app`.`review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product_reviews_app`.`review` ;

CREATE TABLE IF NOT EXISTS `product_reviews_app`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(140) NOT NULL,
  `rating` TINYINT(2) UNSIGNED NULL,
  `active` BIT(1) NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_product_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_review_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `product_reviews_app`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
