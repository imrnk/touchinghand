CREATE TABLE IF NOT EXISTS `ocsa`.`session` (
  `session_id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `session_date` DATETIME NOT NULL,
  `created_on` DATETIME NULL,
  `updated_on` DATETIME NULL,
  PRIMARY KEY (`session_id`, `client_id`),
  UNIQUE INDEX `session_id_UNIQUE` (`session_id` ASC, `client_id` ASC, `session_date` ASC),
  INDEX `fk_session_client_idx` (`client_id` ASC),
  CONSTRAINT `fk_session_client`
    FOREIGN KEY (`client_id`)
    REFERENCES `ocsa`.`client` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'this table holds client\'s session data'
