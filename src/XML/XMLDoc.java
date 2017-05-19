package XML;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

/* load & save */
import org.w3c.dom.ls.*;
import org.xml.sax.SAXException;


/* XML Transformation */
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.OutputKeys;

/**
 * Classe para manipula��o de documentos XML
 */

/**
 * @author Porf�rio Filipe
 *
 */

final public class XMLDoc {

    /**
     * Devolve lista de n�s gerada pela express�o xPath indicada
     *
     * @param expression
     *            xpath
     * @param doc
     *            raiz do documento XML
     * @return
     * 			lista de n�s
     */

    public static final NodeList getXPath(final String expression, final Document doc) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes;
        try {
            nodes = (NodeList) xpath.evaluate(expression, doc,
                    XPathConstants.NODESET);
            return nodes;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Executa uma express�o XPath numa arvore DOM e devolve o 1� string (valor)
     *
     * @param expression
     * @param doc
     * @return string que � o valor do n� encontrado
     *
     */
    public static final String getXPathV(final String expression, final Document doc) {
        NodeList aux = getXPath(expression, doc);
        if (aux == null)
            return null;
        else if (aux.item(0) == null)
            return null;
        else
            return aux.item(0).getTextContent();
    }

    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public static final Document parseFile(final String fileName) {
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
        return doc;
    }


    /**
     * @param DOMtree arvore DOM
     * @param targetFileName ficheiro usado para escrita
     */
    public static final void StartSerialization(final Document DOMtree, final String targetFileName) {
		/*
		 * demonstra��o da funcionalidade save da arvore DOM alternativa ao uso
		 * da transforma��o vazia
		 */
        FileOutputStream FOS = null;
        DOMImplementationLS DOMiLS = null;

        // testing the support for DOM Load and Save
        if ((DOMtree.getFeature("Core", "3.0") != null)
                && (DOMtree.getFeature("LS", "3.0") != null)) {
            DOMiLS = (DOMImplementationLS) (DOMtree.getImplementation()).getFeature(
                    "LS", "3.0");
            System.out.println("[Using DOM Load and Save]");
        } else {
            System.err.println("[DOM Load and Save unsupported]");
            System.exit(0);
        }

        // get a LSOutput object
        LSOutput LSO = DOMiLS.createLSOutput();
        // LSO.setEncoding("UTF-16"); // codifica��o por omiss�o do windows
        LSO.setEncoding("ISO-8859-1"); // codifica��o para portugu�s

        // setting the location for storing the result of serialization

        try {
            FOS = new FileOutputStream(targetFileName);
            LSO.setByteStream((OutputStream) FOS);
            // LSO.setByteStream(System.out); // usa o output para testes
        } catch (java.io.FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        // get a LSSerializer object
        LSSerializer LSS = DOMiLS.createLSSerializer();

        // do the serialization
        boolean ser = LSS.write(DOMtree, LSO);

        // publish the result
        if (ser)
            System.out.println("\n[Serialization done!]");
        else
            System.out.println("[Serialization failed!]");

        try {
            FOS.close();
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Escreve arvore DOM num ficheiro
     *
     * @param input arvore DOM
     * @param output ficheiro usado para escrita
     */

    public static final void writeDocument(final Document input, final String output) {
        try {
            writeDocument(input, new FileOutputStream(output));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param input arvore DOM
     * @param output stream usado para escrita
     */
    public static final void writeDocument(final Document input, final OutputStream output) {
		/* implementa��o da escrita da arvore num ficheiro recorrendo ao XSLT */
        try {
            DOMSource domSource = new DOMSource(input);
            StreamResult resultStream = new StreamResult(output);
            TransformerFactory transformFactory = TransformerFactory
                    .newInstance();

            // transforma��o vazia

            Transformer transformer = transformFactory.newTransformer();

            transformer
                    .setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            if (input.getXmlEncoding() != null)
                transformer.setOutputProperty(OutputKeys.ENCODING,
                        input.getXmlEncoding());
            else
                transformer
                        .setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            try {
                transformer.transform(domSource, resultStream);
            } catch (javax.xml.transform.TransformerException e) {

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Transforma��o XML
     * @param xmlFileName documento XML
     * @param xsltFileName documento XSL
     * @param targetFileName documento gerado
     */
    public static final void transfDoc(String xmlFileName, String xsltFileName,
                                       String targetFileName) {

		/*
		 * System.setProperty("javax.xml.transform.TransformerFactory",
		 * "className"); org.apache.xalan.processor.TransformerFactoryImpl
		 * com.icl.saxon.TransformerFactoryImpl (Saxon 6)
		 * net.sf.saxon.TransformerFactoryImpl (Saxon 7)
		 * oracle.xml.jaxp.JXSAXTransformerFactory
		 */

        try {
            Source input = new StreamSource(xmlFileName);
            Source xsl = new StreamSource(xsltFileName);
            // Result output = new StreamResult(System.out);

            Result output = new StreamResult(targetFileName);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsl);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(input, output);

        } catch (TransformerException te) {
            System.out.println("Transformer exception: " + te.getMessage());
        }
    }

    public static final boolean validDocDTD(String xmlFileName, String vFileName) {
        return validDoc(xmlFileName, vFileName, XMLConstants.XML_DTD_NS_URI);
    }

    public static final boolean validDocXSD(String xmlFileName, String vFileName) {
        return validDoc(xmlFileName, vFileName,
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    /**
     * Valida��o de documento em ficheiro com XSD ou DTD conforme o indicado no parametro type
     * @param xmlFileName
     * @param vFileName
     * @param type XMLConstants.W3C_XML_SCHEMA_NS_URI ou XMLConstants.XML_DTD_NS_URI
     * @return
     */
    private static final boolean validDoc(String xmlFileName, String vFileName,
                                          String type) {
        //System.out.println("Processo xml ("+xmlFileName+") e xsd ("+vFileName+")");
        Source xmlFile = new StreamSource(new File(xmlFileName));
        SchemaFactory schemaFactory = SchemaFactory.newInstance(type);
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(new File(vFileName));
        } catch (SAXException e1) {
            e1.printStackTrace();
            System.out.println("Erro no acesso ao ficheiro"+vFileName);
            return false;
        }
        Validator validator = schema.newValidator();
        try {
            try {
                validator.validate(xmlFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            System.out.println(xmlFile.getSystemId() + " is valid");
            return true;
        } catch (SAXException e) {
            System.err.println(xmlFile.getSystemId() + " is NOT valid");
            System.err.println("Reason: " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Valida��o de documento na �rvore DOM, com XSD ou DTD conforme o indicado no parametro type
     * @param document
     * @param xsdFileName
     * @param type XMLConstants.W3C_XML_SCHEMA_NS_URI ou XMLConstants.XML_DTD_NS_URI
     * @return sucesso/insucesso
     * @throws SAXException
     */
    public static final boolean validDoc(Document document, String xsdFileName, String type) throws SAXException {

        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(type);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File(xsdFileName));
        Schema schema = factory.newSchema(schemaFile);

        // create a Validator instance, which can be used to validate an instance document
        Validator validator = schema.newValidator();


        // validate the DOM tree
        try {
            validator.validate(new DOMSource(document));
            return true;
        } catch (IOException e) {
            // instance document is invalid!
            return false;
        }
    }

    public static final String ListarFicheiros(String pasta) {
        String result = "<?xml version='1.0' encoding='ISO-8859-1' standalone='yes'?>\n";
        result = result + "<ficheiros>\n";
        File folder = new File(pasta);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
            if (listOfFiles[i].isFile())
                result = result + "<ficheiro>" + listOfFiles[i].getName() + "</ficheiro>\n";
        return result + "</ficheiros>\n";
    }

    public static void main(String[] args) {

        System.out.println(ListarFicheiros("WebContent\\xml"));


		/* String contexto = "WebContent\\xml";

		String xmlFileName = contexto + "exemplo.xml";
		String xsdFileName = contexto + "exemplo.xsd";
		String dtdFileName = contexto + "exemplo.dtd";

		String xsltHTMFileName = contexto + "exemplo.xsl";

		String targetFileName = contexto + "save.xml";
		String HTMFileName = contexto + "exemplo.htm"; */



		/*
		if (validDocXSD(xmlFileName, xsdFileName))
			System.out.println("Valida��o com XSD realizada com sucesso!");
		else
			System.out.println("Falhou a valida��o com XSD ("+xsdFileName+")!");

		if (validDocDTD(xmlFileName, dtdFileName))
			System.out.println("Valida��o com DTD realizada com sucesso!");
		else
			System.out.println("Falhou a valida��o com DTD ("+xsdFileName+")!");

		transfDoc(xmlFileName, xsltHTMFileName, HTMFileName);
		*/

    }

}
