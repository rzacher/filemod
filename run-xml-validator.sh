# Run the xmlvalidator
# arguments: <xsd-file> <xml-file>
# Example usage
# java -jar ./target/xmlvalidate-1.0.2-jar-with-dependencies.jar ./src/test/Employee.xsd    ./src/test/EmployeeRequest.xml
XML_FILE="$1"
OUTPUT_DIR="$2"
java -jar ./target/filemod-*-jar-with-dependencies.jar  ${XML_FILE}  ${OUTPUT_DIR}
