Spring Boot readme.txt

2016-03-02 MG


Prerequisites

	1. Check if m2e (maven plugin) is installed. If not - install it from:
		http://download.eclipse.org/technology/m2e/releases

	2. If installed successfully Maven icon will appear on project and Maven context menus will apper

	3. Optional - Update Maven dependencies (ALT + F5)


How to run

	Run As Java Application the main application class:
		com.broadsoft.ums.boot.Application

How to build:

	Right click on the project -> context menu will appear. Click on "Maven Install" menu.
	
	The following info will be shown in the console if everything is successful
	
	[INFO] Scanning for projects...
	[INFO]                                                                         
	[INFO] ------------------------------------------------------------------------
	[INFO] Building com.broadsoft.ums.boot 0.0.1-SNAPSHOT
	[INFO] ------------------------------------------------------------------------
	[INFO] 
	[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ com.broadsoft.ums.boot ---
	[INFO] Using 'UTF-8' encoding to copy filtered resources.
	[INFO] Copying 0 resource
	[INFO] Copying 0 resource
	[INFO] 
	[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ com.broadsoft.ums.boot ---
	[INFO] Nothing to compile - all classes are up to date
	[INFO] 
	[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ com.broadsoft.ums.boot ---
	[INFO] Using 'UTF-8' encoding to copy filtered resources.
	[INFO] Copying 0 resource
	[INFO] 
	[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ com.broadsoft.ums.boot ---
	[INFO] Nothing to compile - all classes are up to date
	[INFO] 
	[INFO] --- maven-surefire-plugin:2.18.1:test (default-test) @ com.broadsoft.ums.boot ---
	[INFO] 
	[INFO] --- maven-jar-plugin:2.5:jar (default-jar) @ com.broadsoft.ums.boot ---
	[INFO] Building jar: D:\eclipse\workspace\spring-boot\com.broadsoft.ums.boot\target\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar
	[INFO] 
	[INFO] --- spring-boot-maven-plugin:1.3.2.RELEASE:repackage (default) @ com.broadsoft.ums.boot ---
	[INFO] 
	[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ com.broadsoft.ums.boot ---
	[INFO] Installing D:\eclipse\workspace\spring-boot\com.broadsoft.ums.boot\target\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar to C:\Users\mgeorgiev\.m2\repository\com\broadsoft\ums\boot\com.broadsoft.ums.boot\0.0.1-SNAPSHOT\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar
	[INFO] Installing D:\eclipse\workspace\spring-boot\com.broadsoft.ums.boot\pom.xml to C:\Users\mgeorgiev\.m2\repository\com\broadsoft\ums\boot\com.broadsoft.ums.boot\0.0.1-SNAPSHOT\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.pom
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 3.903s
	[INFO] Finished at: Wed Mar 02 16:17:59 EET 2016
	[INFO] Final Memory: 15M/307M
	[INFO] ------------------------------------------------------------------------
	
	
	The following line contains the target location of the jar file built:
	
	[INFO] Installing D:\eclipse\workspace\spring-boot\com.broadsoft.ums.boot\target\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar to C:\Users\mgeorgiev\.m2\repository\com\broadsoft\ums\boot\com.broadsoft.ums.boot\0.0.1-SNAPSHOT\com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar


How to run outside eclipse:

	java -jar com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar

How to get help

	java -jar com.broadsoft.ums.boot-0.0.1-SNAPSHOT.jar ?

