/* $ID: Test.java,v 1.0 18.01.2004 18:06:37 hohwille Exp $ */
package net.sf.mmm.xml.test;

import java.io.StringWriter;

import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;
import net.sf.mmm.xml.impl.DomXmlWriter;
import net.sf.mmm.xml.impl.OutputXmlWriter;

import org.w3c.dom.Document;

/**
 * TODO This class...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class Test {

    /**
     *  
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void toXml(XmlWriterIF serializer) throws XmlException {
        serializer.writeStartElement("tag1");
        serializer.writeAttribute("atr", "val");
        serializer.writeNamespaceDeclaration("ns3", "http://URI3");
        serializer.writeCharacters("Hello World!&<>äöüßÄÖÜ");
        serializer.writeStartElement("tag2", "ns2", "http://URI2");
        serializer.writeAttribute("atr", "val&<>\"");
        serializer.writeCharacters("Hello World!");
        serializer.writeEndElement("tag2", "ns2");
        serializer.writeStartElement("tag3", "ns3");
        serializer.writeAttribute("atr", "val");
        serializer.writeEndElement("tag3", "ns3");
        serializer.writeEndElement("tag1");        
    }
    
    
    /**
     * The main method.
     * 
     * @param args are the command line arguments.
     * @throws Exception if something goes wrong.
     */
    public static void main(String[] args) throws Exception {
        StringWriter sw = new StringWriter();
        //OutputStream out = new FileOutputStream("berta.xml");
        //XmlSerializerIF s = new OutputXmlSerializer(out, "", "UTF-8");
        XmlWriterIF s = new OutputXmlWriter(sw, "", "UTF-8");
        toXml(s);
        System.out.println(sw.toString());
        //out.close();
        System.out.println("----");
        Document doc = DomUtil.createDocument();
        s = new DomXmlWriter(doc);
        toXml(s);
        DomUtil.writeXml(doc, System.out, true);
    }
}
