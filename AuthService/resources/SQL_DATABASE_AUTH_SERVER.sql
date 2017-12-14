create database `authservice`;  
use `authservice`;  

CREATE TABLE `authservice`.`users` (
  `username` varchar(50) DEFAULT NULL,
  `pswd` varchar(100) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `usertype` varchar(1) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `encrypt_username` varchar(200) DEFAULT NULL,
  `key1` varchar(200) DEFAULT NULL,
  `key2` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `authservice`.`users` (`username`,`pswd`,`fname`,`usertype`,`token`,`encrypt_username`,`key1`,`key2`) VALUES ('smarth.katyal','Myname123','Smarth','N','RhtR8j2jEqWx6FpjfH1JbLzv65rPfdvL2nu5Yn/Vaz0=','eJWt7fMvsHGGmZFnzsBrQQ==','[B@7b5cea9','[B@2c451119');
commit;
select * from `authservice`.`users`;