
package com.brandsure.filemod;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*; 
import java.util.zip.DataFormatException;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import org.xml.sax.ErrorHandler; 
import org.xml.sax.InputSource; 

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator; 

public class FileMod {
    String xmlFile;
    String outputDir; 

    static Logger logger = Logger.getLogger(FileMod.class);

    public static void main(String[] args) {
      
      FileMod filemod = new FileMod();
   
      // Load the arguments
      filemod.loadArgs(args);
      // Run the validation
      filemod.run(); 
    }

    /**
     * This argument parsing is pretty primitive and should be improved. 
     * @param args
     */
    public void loadArgs(String[] args) {
      int index = 0; 
      logger.info("Printing command line args"); 
      for (String arg: args) {
    	  logger.debug(index + " " + arg );
    	  index++; 
      }
      if ((args.length != 2)){
         printUsage();
      } else {
    	xmlFile = args[0];	
    	outputDir = args[1]; 
        logger.info("xmlFile: " + xmlFile);
      }
    }

    /**
     * Initialize stuff
     */
    private void init() {
    	initLogger();
    }
    
    public void run() {
      try {
        init();
         
        Document doc = loadDocument();

         if (doc == null) {
           logger.error(xmlFile + " wellformed FAIL");
         }    
         
         /**
         <gs1ushc:dscsaTransactionStatement>  
         <gs1ushc:affirmTransactionStatement>true</gs1ushc:affirmTransactionStatement>
         </gs1ushc:dscsaTransactionStatement>
         **/
         Node dscsaTransactionStatement= doc.createElement("gs1ushc:dscsaTransactionStatement");
         Element epcisHeader = (Element) doc.getElementsByTagName("EPCISHeader").item(0);
         epcisHeader.appendChild(dscsaTransactionStatement);
         Node affirmTransactionStatement = doc.createElement("gs1ushc:affirmTransactionStatement");
         Text text = doc.createTextNode("true");
         affirmTransactionStatement.appendChild(text);
         dscsaTransactionStatement.appendChild(affirmTransactionStatement);
         
         // retrieve the element we want to remove
	        NodeList testNameElements  = doc.getElementsByTagName("gwc:TestName");
	   System.out.println("length=" + testNameElements.getLength());
	        for (int i=testNameElements.getLength()-1; i>-1; i--) {
	        	// remove the specific node
	        	Element element = (Element) testNameElements.item(i); 
	        	System.out.println(element);
	        	Node parent = element.getParentNode();
	        	parent.removeChild(element);
	        	parent.appendChild(element); 
	        }
	        
	        NodeList exResElements = doc.getElementsByTagName("gwc:ExRes");
	        for (int j=exResElements.getLength()-1; j>-1; j--) {
	        	// remove the specific node
	        	Element element = (Element) exResElements.item(j); 
	        	Node parent = element.getParentNode();
	        	parent.removeChild(element);
	        	parent.appendChild(element);
	        }
	        
	        // Remove whitespace
	        /**
	         XPath xp = XPathFactory.newInstance().newXPath();
	         NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

	         for (int i=0; i < nl.getLength(); ++i) {
	             Node node = nl.item(i);
	             node.getParentNode().removeChild(node);
	         }
	         **/
	        // Now append 
	        
	        // Normalize the DOM tree, puts all text nodes in the
	        // full depth of the sub-tree underneath this node
	        doc.normalize();

	        prettyPrint(doc);
      } catch (Exception e) {
        logger.error("Caught exception processing xmlFile " + xmlFile + ". " + e.getMessage());
        e.printStackTrace(); 
        System.exit(1); // program failed
      }
    }

    private void initLogger() {
        //DOMConfigurator is used to configure logger from xml configuration file
        DOMConfigurator.configure("log4j.xml");
 
        //Log in console in and log file
        //logger.debug("Log4j appender configuration is successful !!");
    }

    public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes"); 
        Result dest = new StreamResult(new File("xmlFileName.xml"));
        tf.transform(new DOMSource(xml), dest);
        //System.out.println(out.toString());
    }
    //https://stackoverflow.com/questions/6362926/xml-syntax-validation-in-java
    // This method checks if the xml file is well formed. 
    // Returns the document is parsing is successful. 
    // Returns null if parsing for well formed fails. 
    private Document loadDocument() {
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

       // builder.setErrorHandler(new SimpleErrorHandler());    
        // the "parse" method also validates XML, will throw an exception if misformatted

        Document document = builder.parse(new InputSource(xmlFile)); 
        logger.info(xmlFile + " wellformed PASS"); 
        return document;    
        } catch (IOException ie) {
          logger.error("Caught IOxception loading xmlFile " + xmlFile + ". " + ie.getMessage());
          ie.printStackTrace(); 
          return null; 
        } catch (ParserConfigurationException pce)  {
          logger.error("Caught exception loading xmlFile " + xmlFile + ". " + pce.getMessage());
          pce.printStackTrace(); 
          return null; 
        } catch (SAXException se) {
          logger.error("Caught exception loading xmlFile " + xmlFile + ". " + se.getMessage());
          se.printStackTrace();
          return null;
        }
    }

    private void printUsage() {
       System.out.println("Usage: command line arguments = -d epcisDirectory  xmlFilename -o outputDir"); 
    }
    
   
}

