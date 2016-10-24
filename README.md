# springboot-dynamodb

This is proof of concept code to test message history service with DynamoDB and to provide REST with Springboot.
Springboot framework is based on example shown in 
https://beach.mtl.broadsoft.com/svn/working/UC-ONE/springBoot/com.broadsoft.ums.boot

Note of architecture concept is in Google Drive: UMS > AWS > Kujin's Note
https://drive.google.com/drive/folders/0B1orgW7azzDnMzFqME1qQkdoVVk

Steps to experiment
(1) Import this branch to eclipse
(2) Select project (springboot-synamodb-experiment) Right-click: Run As> Maven Install
	This will compile and install artifacts to maven repo
(3) Select project (springboot-synamodb-experiment) Right-click: Run As> Java Application > Select class Application (which has main())

FIXME: This code is not working yet. 
	- http://localhost:8080/swagger-ui.html does not show REST API declared in MessageHistoryController

