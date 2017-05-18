package Servidor.db;


import XML.XMLDoc;
import org.w3c.dom.Document;

public class DbManager {

    public synchronized void writeToDB(Document d, String filename){
        XMLDoc.writeDocument(d, filename);
    }

    public synchronized Document readFromDB(String filename){
        return XMLDoc.parseFile(filename);
    }
}
