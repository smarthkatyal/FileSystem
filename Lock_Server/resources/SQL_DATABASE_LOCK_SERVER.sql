create database `lockservice`;  
use `lockservice`; 

CREATE TABLE `lockservice`.`lookup` (
  `username` varchar(100) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `emailclient` varchar(100) DEFAULT NULL,
  `locked` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `lockservice`.`lookup` ADD PRIMARY KEY (username,filename);
INSERT INTO `lockservice`.`lookup` (`username`,`filename`,`emailclient`,`locked`) VALUES ('eJWt7fMvsHGGmZFnzsBrQQ==','SecurityFunctions.txt','hello@gmail.com','N');

commit;

select * from `lockservice`.`lookup`;