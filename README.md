# Distributed File System
========================


Hey! 
This repository contains the code for the distributed file systems task.  This readme contains information about **how to correctly configure, compile and run the program.** The code was written using **Java 1.8**


>#### <i class="icon-upload"></i> Submission details:
Name: Smarth Katyal
<br>TCD Student ID: 17306092
<br>Email: katyals@tcd.ie

Dependencies
-------------
>- **Java 1.8** (Performance with Java version 1.7 is untested)
>- **Maven 3.3** 
>- **Apache Tomcat Server 9.0**
>- **Connection to internet without restrictions** (Required so that maven can download the dependencies)
>- **MySQL Server 6** (For database)
>- **A Web Browser for running the client**

Configuration & Compilation & Running
-------------------------------------
>- Do the below steps for all 5 projects
>-  Navigate to the project root directory for every project.
>- Run the below command to generate 'war' file
<br>**mvn clean install**
>- Copy the '*.war' file generated to the webapps folder of Tomcat for that particular project.
>- Configure the tomcat ports in server.xml of Tomcat to be different than that of the previously used Tomcat.
>- Repeat for other projects
>- Run the SQL scripts in the resources folder of each project on the MySQL database server using any client like 'MySql Workbench'
>- Now each project has **config.properties** in the **resources** directory of the project. Configure it correctly to enter the URL of the servers. Database configurations also need to be done here. Do this for all projects.
>- Start each tomcat. The client should be accessible using the URL: <IP_OF_CLIENT_SERVER>:<PORT>/FileSystemClient/ and login page should appear.
>- Currently, only the username: 'Smarth.Katyal' & Password: 'Myname123' is added to the database(case sensitive).



Modules Implemented
---------------------
1) Client Server
2) Lock Service
3) Authentication Service
4) Directory Service
5) Caching
6) File Storage Server

WORKING
---------------------
___________________________________________________________________________________________________

Login
---------------------
>- The client sends the username and the password(both encrypted with the password) to the Auth Server. 
>- The auth Server checks the encrypted username against the already encrypted username store in its database. If successfully validated, it encrypts the password in the database with the password itself and matches it with the password given by the user. If successful, it generates a key1, key2 and a token(consisting of userid and timestamp). The token is encrypted with key2 and the key1, and encrypted token is sent back to the client.
>- The client should use key1 to encrypt all all further communication. It should also send the token as piggyback information for all other requests so that destination server can check validity of token. Key2 is never made public and is kept with Authentication server.

Read File
---------------------
>- After successful login, client can do read or write operation. 
**Caching**
>-The client first checks the filename entered by the user and checks whether it exists in cache. If it does, the file is served from the cache. 
**Reading from Storage Server**
>-For read, the client enters the name of the file and encrypts it with key1, and sends it along with the token to directory service. The directory service sends the token to the auth server so that it can check the validity of token and return key1. The directory service uses this key1 to decrypt the filename and searches its database if the file exists. If it does, the DS gives the client the serverIp and directory of the file(both encrypted with key1).
>- The client then sends a read request to the storage server along with the filename(encrypted with key1) and the token. The Storage service sends a request to the Auth server to validate the token and recieves the key1 to decrypt the filename. It then reads the file contents from the disk and encrypts it with key1 and sends it back to the client.


Write File
---------------------
>- After successful login, client can do read or write operation. 
>-For read, the client enters the name of the file and encrypts it with key1, and sends it along with the token to directory service. The directory service sends the token to the auth server so that it can check the validity of token and return key1. The directory service uses this key1 to decrypt the filename and searches its database if the file exists. If it does, the DS gives the client the serverIp and directory of the file(both encrypted with key1).
**Locking**
>-The client then sends a request to the lock server with the filename(encrypted with key1) to lock the file. If it is already locked, the client is displayed an error message and asked to retry. 
>-If lock is obtained successfully, the client sends a read request to Storage server to get the contents of the file. If file does not exist, it is created at the location specified by the directory server. 
>- User can then make changes to the contents of the file in the web browser itself. When the user submits the changes, the client calls the File Storage server with the new file content(encrypted with key1) and token to the storage server. The storage server validates the token with the Auth server and gets key1 in response and decrypts the new file contents. It then writes to the file.
>- After write is completed, client sends a request to the lock server to release the lock so that file is writable again. The client also checks if the file is in cache and updates it with the new content so that future read requests never get obsolete data.



More Information
---------------------
**Security**
>- All requests and responses are encrypted.
>-The encryption is done using a 3 key based system. Key 1 is generated by Auth server after successful validation. It is shared with client and other servers.
>-Key 2 is only known to the auth server and is used only to encrpy and decrypt the token.
>-Key 3 is the password of the client itself. This is used only during the inital authentication of client with the auth server.
>-Token has userid and timestamp. Whenever a token is sent to Auth server for validation, it checks if the token is more than 5 minuts old, it refuses the request.

**Caching**
>-Caching is implimented on the client server. The the acutal client is just a browser and storing in cookies is not the best idea, caching was done only on the client server. 
>-A file is cached only when it is read by any user. There is a configurable limit to the number of files that can be cached depending on the memory of the server since it is in memory caching. 
>-Since all read/write requests land on the same client server, cache can be easily cleared/updated when there is a write operation.

