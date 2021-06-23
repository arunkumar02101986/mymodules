Design:
=======
To implement the conversion of the data from json format to xml format and save the data in a file with extension as xml. The design is to check the data coming in json format is on type number, string, boolean, null,object and array and generate the respective xml element based on set of Rules.
 

Steps to Build the Project:
===========================
1. Check for Java version as 1.7(by running command: java -version). If Java 1.7 not installed, Please install it and check the version.
2. Install Maven 3.8.1 or higher version to build the project.
3. Place the project in a location.
4. open the command Prompt or terminal. Go inside the project(by giving command: cd JsontoXmlConverter)
5. check maven install in the system (by running command: mvn -version)
6. Run the command "mvn clean package" in the command prompt.
7. Go to target folder and check for the runnable jar file:- "xmltojsonconverter.jar"(path:./JsontoXmlConverter/target/xmltojsonconverter.jar)
8. Running the "xmltojsonconverter.jar" by passing 2 parameters as below.
	java -jar xmltojsonconverter.jar input.json output.xml
        where input.json is the input json file with absolute path or just file name and
	      output.xml is the output xml file with absolute path or just file name	

Note: 
Implementating this project, add json-simple jar for identifying the given json is object or array and used onejar-maven-plugin for creating
the jar with all dependencies as one-jar. 


Test Cases:
===========
Based on the generated jar, the input json files are passed from "input" folder and respective xml files are generated in the "output" folder. 
Both folder are placed outside the project to understand the test scenario coverage for different cases.