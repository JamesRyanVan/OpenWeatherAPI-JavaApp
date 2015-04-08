OpenWeatherApp
=======

What is OpenWeatherApp?
-----------
OpenWeatherApp is a weather viewing program that allows a user to view the current weather, long-term and short-term forecast for multiple locations. 

The program developed in Java with the Swing GUI. The program gets weather information from the OpenWeatherMap api. It makes use of Jyloo Software's Synthetica (version 2.19.1) and Synthetica AluOxide (version 2.9.7) Java Look and Feel and json.org Java library (version 20090211).

Download and Install
-----------
Download the latest version (3.0) at https://github.com/UWO-2212-W2015/team10/tree/master/build. 

The program comes with the necessary dependencies to execute. No installation is needed.

You must have Java 1.7 or above to run OpenWeatherApp. To run the application, go to the folder containing the downloaded jar and type: java -jar 10_OpenWeatherApp-3.0-jar-with-dependencies.jar

Build
-----------

To build the program download the source code archive file (zip/tar.gz) at https://github.com/UWO-2212-W2015/team10/releases/.
Extract it. Ensure maven 3.0 or higher is installed. From a command line program, change to the main directory containing pom.xml and type: "mvn validate" then "mvn package". This will be build a jar file in the target directory.

The following dependencies were included in pom.xml:

<dependencies>
<dependency>
	<groupId>org.json</groupId>
	<artifactId>json</artifactId>
	<version>20090211</version>
</dependency>

<dependency>
        <groupId>synthetica</groupId>
        <artifactId>synthetica-main</artifactId>
        <version>2.19.1</version>
</dependency>

<dependency>
        <groupId>synthetica</groupId>
        <artifactId>synthetica-AluOxide</artifactId>
        <version>2.9.7</version>
</dependency>

</dependencies>

Usage Example
-----------
Please see our video at: https://youtu.be/oIVq32y900Y

Documentation
-----------

The JavaDoc can be viewed by opening index.html in the project source code's 'doc' folder.






