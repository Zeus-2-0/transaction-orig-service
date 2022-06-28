DROP TABLE  IF EXISTS `transactionorigdb`.`transaction`;
CREATE TABLE IF NOT EXISTS `transactionorigdb`.`transaction` (
  `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The primary key of the table',
  `zfcn` VARCHAR(100) NOT NULL COMMENT 'The Zeus file control number',
  `interchange_control_number` VARCHAR(100) NOT NULL COMMENT 'The interchange control number received in the file',
  `ztcn` VARCHAR(100) NOT NULL COMMENT 'Zeus transaction control number',
  `transaction_control_number` VARCHAR(100) NOT NULL COMMENT 'The transaction control number received in the file',
  `file_sk` VARCHAR(36) NOT NULL COMMENT 'The primary of the file from the file management service',
  `trading_partner_sk` VARCHAR(36) NOT NULL COMMENT 'The primary key of the trading partner that is associated with the transaction',
  `trading_partner_id` VARCHAR(100) NOT NULL COMMENT 'The trading partner id of the transaction',
  `line_of_business_type_code` VARCHAR(50) NOT NULL COMMENT 'The line of business of the transaction',
  `state_type_code` VARCHAR(50) NOT NULL COMMENT 'The state of the transaction',
  `marketplace_type_code` VARCHAR(50) NOT NULL COMMENT 'The marketplace of the transaction',
  `interchange_sender_id` VARCHAR(100) NOT NULL COMMENT 'The interchange sender id received in the transaction',
  `interchange_receiver_id` VARCHAR(100) NOT NULL COMMENT 'The interchange receiver id received in the transaction',
  `group_sender_id` VARCHAR(100) NOT NULL COMMENT 'The group sender id received in the transaction',
  `group_receiver_id` VARCHAR(100) NOT NULL COMMENT 'The group received id received in the transaction',
  `transaction_data` LONGTEXT NOT NULL COMMENT 'The transaction data as JSON content',
  `created_date` DATETIME NULL COMMENT 'The date when the record was created',
  `updated_date` DATETIME NULL COMMENT 'The date when the record was updated',
  PRIMARY KEY (`transaction_sk`))
ENGINE = InnoDB
COMMENT = 'The table contains the high level details of the transaction and the json content of the transaction'