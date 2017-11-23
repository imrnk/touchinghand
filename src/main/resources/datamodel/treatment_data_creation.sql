CREATE TABLE IF NOT EXISTS `ocsa`.`treatment_data` (
  `treatment_id` INT NOT NULL AUTO_INCREMENT,
  `client_id` INT NOT NULL,
  `session_id` INT NOT NULL,
  `physical_comp` VARCHAR(200) NULL,
  `mental_comp` VARCHAR(500) NULL,
  `onset_date` DATETIME NULL,
  `duration` VARCHAR(45) NULL,
  `degree` INT NULL,
  `therapy_applied` VARCHAR(100) NULL,
  `casehistory` VARCHAR(400) NULL,
  `psy_evaluation` VARCHAR(250) NULL,
  `lab_testing` VARCHAR(200) NULL,
  `diagnosis` VARCHAR(200) NULL,
  `diff_diagnosis` VARCHAR(200) NULL,
  `formulation` VARCHAR(200) NULL,
  `impression` VARCHAR(400) NULL,
  `feedback` VARCHAR(500) NULL,
  `created_on` DATETIME NOT NULL,
  `updated_on` DATETIME NULL,
  PRIMARY KEY (`treatment_id`, `client_id`, `session_id`),
  UNIQUE INDEX `treatment_id_UNIQUE` (`treatment_id` ASC),
  INDEX `fk_treatment_client_id_idx` (`client_id` ASC),
  INDEX `fk_treatment_session_id_idx` (`session_id` ASC),
  CONSTRAINT `fk_treatment_client_id`
    FOREIGN KEY (`client_id`)
    REFERENCES `ocsa`.`client` (`client_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_treatment_session_id`
    FOREIGN KEY (`session_id`)
    REFERENCES `ocsa`.`session` (`session_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'all treatment related data of a client'