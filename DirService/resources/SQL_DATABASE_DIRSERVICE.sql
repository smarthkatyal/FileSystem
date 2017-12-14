create database dirservice;
use dirservice;
DROP TABLE `dirservice`.`filelist`;
CREATE TABLE `dirservice`.`filelist` (
  `filename` VARCHAR(50) NOT NULL,
  `server` VARCHAR(45) NOT NULL,
  `lockstatus` VARCHAR(1) NOT NULL,
  `modified_on` VARCHAR(45) NULL,
  `directory` VARCHAR(100) NULL, 
  PRIMARY KEY (`filename`));
commit;