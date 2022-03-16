DROP TABLE  IF EXISTS `transactionorigdb`.`transaction_info`;
CREATE TABLE IF NOT EXISTS `transactionorigdb`.`transaction_info` (
    `transaction_info_sk` VARCHAR(36) NOT NULL,
    `file_id` VARCHAR(100) NULL,
    PRIMARY KEY (`transaction_info_sk`))
    ENGINE = InnoDB