# xmlValidation
Application to Read xml and validate against xsd

README for xml-validator
Author: Bob Zacher
Property of Brandsure. 

xml-validator is a java program for validating an xml against an xsd file

To use the program, you must first install java 1.8. 
Java is probably already installed on your computer. 

To see if it is installed, type 
java -version. 

If you get a version, then it is installed. 

If it is not installed, you can download it here. 
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
This is the Java JDK which is needed if you want to compile the program. 
If you just want to run the program, you just need the Java JRE. 

To run the program. 

Unzip the file xmlvalidate-<version>-1.0.tar.gz. 
Use any zip prgram. e.g winzip or download 7-zip if winzip is not installed. 
After unzipping, cd to the directory xmlvalidate-<version> e.g xmlvalidate-1.0.1

To run the program on windows, type
run-xml-validator.bat -f <xsd-file> <xml-file> -o <reports-output-dir>

for example, to run against a good xml file in the test directory, 
type
run-xml-validator.bat -f ./test/Employee.xsd ./test/GoodEmployeeRequest.xml
The result should be an output value of true


for example, to run against a bad xml file in the test directory, 
type
run-xml-validator.bat -f ./test/Employee.xsd ./test/BadEmployee.xml -o reports
The result should be an output value of false.

Running tests while building
./run-xml-validator.sh -f ./src/test/Employee.xsd ./src/test/GoodEmployeeRequest.xml

To run against an epcis directory use the -d command line flag
./run-xml-validator.sh -d epcis ./src/test/epcis/Scenario_Test_03-1.2.xml -o reports

See the testCommands.txt for examples of the text commands. 


Release Notes
version 1.0.1 Initial Release
version 1.0.2
Checks if document is wellformed. 
Added logging capability

version 1.1.1
modified to use epcis directory. Added command line args -d for epcis dir and -f for xsd file

version 1.2.1
10/4/18
Now generates simple text report in same dir as xml file

version 1.3.1
10/11/18
Added required command line arguments -o <report-output-dir> so the output directory can be specified. 







