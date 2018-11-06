REM Script to launch XML validator

SET JARFILE=.\lib\xmlvalidate-1.3.1-jar-with-dependencies.jar

SET FLAG=%1
SET XSD=%2
SET XML_FILE=%3
SET OUTPUT_FLAG=%4
SET OUTPUT_DIR=%5

echo  "%FLAG%"
echo  "%XSD%" 
echo  "%XML_FILE%"
echo  "%OUTPUT_FLAG%"
echo  "%OUTPUT_DIR%"

java -jar %JARFILE% %FLAG% %XSD% %XML_FILE% "%OUTPUT_FLAG%" "%OUTPUT_DIR%"
