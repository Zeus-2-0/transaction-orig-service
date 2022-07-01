DROP TABLE  IF EXISTS `transactionorigdb`.`transaction`;
DROP TABLE  IF EXISTS `transactionorigdb`.`functional_group`;
DROP TABLE  IF EXISTS `transactionorigdb`.`interchange`;
CREATE TABLE IF NOT EXISTS `transactionorigdb`.`interchange` (
  `interchange_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
  `file_sk` VARCHAR(36) NOT NULL COMMENT 'The primary key of the file that was created by the file management service',
  `zfcn` VARCHAR(100) NOT NULL COMMENT 'The zeus file control number',
  `interchange_control_number` VARCHAR(100) NOT NULL COMMENT 'The interchange control number received for the file',
  `interchange_date` VARCHAR(45) NULL COMMENT 'Interchange creation date',
  `interchange_time` VARCHAR(45) NULL COMMENT 'Interchange creation time',
  `interchange_sender_id` VARCHAR(100) NULL COMMENT 'The interchange sender id received in the transaction',
  `interchange_receiver_id` VARCHAR(100) NULL COMMENT 'The interchange receiver id received in the transaction\n',
  `trading_partner_id` VARCHAR(100) NULL COMMENT 'The trading partner id associated with the file',
  `line_of_business_type_code` VARCHAR(100) NULL COMMENT 'The line of business type code associated with the transaction',
  `state_type_code` VARCHAR(100) NULL COMMENT 'The state from which the file was received',
  `marketplace_type_code` VARCHAR(100) NULL COMMENT 'The marketplace type that is associated with the file',
  `number_of_functional_groups` INT NULL,
  `created_date` DATETIME NULL,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`interchange_sk`))
ENGINE = InnoDB
COMMENT = 'The interchange details that were received in the file';
CREATE TABLE IF NOT EXISTS `transactionorigdb`.`functional_group` (
  `functional_group_sk` VARCHAR(36) NOT NULL COMMENT 'The primary key of the table',
  `interchange_sk` VARCHAR(36) NOT NULL COMMENT 'The sk that connects back to the interchange envelope that the group was received in.',
  `group_receiver_id` VARCHAR(100) NOT NULL COMMENT 'The group receiver id received in the transaction',
  `group_sender_id` VARCHAR(100) NOT NULL COMMENT 'The group sender id received in the transaction',
  `group_control_number` VARCHAR(100) NULL COMMENT 'The group control number received in the file',
  `group_creation_date` VARCHAR(45) NULL COMMENT 'Group creation date',
  `group_creation_time` VARCHAR(45) NULL COMMENT 'Group creation time',
  `number_of_transactions` INT NULL,
  `created_date` DATETIME NULL COMMENT 'Date when the record was created',
  `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
  PRIMARY KEY (`functional_group_sk`),
  INDEX `interchange_fk_idx` (`interchange_sk` ASC) VISIBLE,
  CONSTRAINT `interchange_fk`
    FOREIGN KEY (`interchange_sk`)
    REFERENCES `transactionorigdb`.`interchange` (`interchange_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This table contains the group segments received in the file.';
CREATE TABLE IF NOT EXISTS `transactionorigdb`.`transaction` (
  `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The primary key of the table',
  `functional_group_sk` VARCHAR(36) NOT NULL COMMENT 'The functional group that the transaction belongs to',
  `transaction_control_number` VARCHAR(100) NOT NULL COMMENT 'The transaction control number received in the file',
  `ztcn` VARCHAR(100) NULL COMMENT 'The unique control number created for the transaction. This is created by the transaction origination service and can be used to track the transaction across services. This is a unique id that will not be repeated for any transactions',
  `transaction_data` LONGTEXT NOT NULL COMMENT 'The transaction data as JSON content',
  `created_date` DATETIME NULL COMMENT 'The date when the record was created',
  `updated_date` DATETIME NULL COMMENT 'The date when the record was updated',
  PRIMARY KEY (`transaction_sk`),
  INDEX `functional_group_fk_idx` (`functional_group_sk` ASC) VISIBLE,
  CONSTRAINT `functional_group_fk`
    FOREIGN KEY (`functional_group_sk`)
    REFERENCES `transactionorigdb`.`functional_group` (`functional_group_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'The table contains the high level details of the transaction and the json content of the transaction';