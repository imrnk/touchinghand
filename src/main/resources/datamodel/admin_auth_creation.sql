CREATE TABLE IF NOT EXISTS `ocsa`.`admin` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `full_name` VARCHAR(64) NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'admin/counsellor info';

CREATE TABLE IF NOT EXISTS `ocsa`.`authorities` (
  `username` VARCHAR(45) NOT NULL,
  `authority` VARCHAR(45) NULL,
  INDEX `fk_authorities_users_idx` (`username` ASC),
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `ocsa`.`admin` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'user authorities';

