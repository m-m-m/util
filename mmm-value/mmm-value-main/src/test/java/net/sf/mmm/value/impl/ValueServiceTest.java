/* $ Id: $ */
package net.sf.mmm.value.impl;

import java.io.StringWriter;
import java.util.Date;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.util.xml.DomUtil;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueServiceIF;
import net.sf.mmm.value.impl.ValueService;
import net.sf.mmm.value.impl.type.XmlValueManager;
import net.sf.mmm.xml.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;
import net.sf.mmm.xml.impl.DomXmlWriter;
import net.sf.mmm.xml.impl.OutputXmlWriter;

/**
 * This is the test case for {@link net.sf.mmm.value.impl.ValueService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ValueServiceTest extends TestCase {

    /** the value service instance */
    private ValueServiceIF service;

    /**
     * The constructor.
     */
    public ValueServiceTest() {

        super();
    }

    /**
     * @see junit.framework.TestCase#setUp()
     * 
     */
    @Override
    protected void setUp() throws Exception {

        super.setUp();
        ValueService serviceImpl = new ValueService();
        serviceImpl.initialize();
        this.service = serviceImpl;
    }

    /**
     * This method produces some strange xml for testing.
     * 
     * @param xmlWriter
     *        is the serializer where to create the xml.
     * @throws XmlException
     *         if the xml is illegal or general error.
     */
    public static void toXml(XmlWriterIF xmlWriter) throws XmlException {

        String ns3 = "http://URI3";
        // xmlWriter.writeStartElement(ValueManagerIF.XML_TAG_VALUE);
        // xmlWriter.writeAttribute(ValueManagerIF.XML_ATR_VALUE_NAME,
        // XmlValueManager.VALUE_NAME);
        xmlWriter.writeStartElement("tag1");
        xmlWriter.writeNamespaceDeclaration("ns3", ns3);
        xmlWriter.writeAttribute("atr", "val");
        xmlWriter.writeCharacters("Hello World!&<>äöüßÄÖÜ");
        xmlWriter.writeStartElement("tag2", "ns2", "http://URI2");
        xmlWriter.writeAttribute("atr", "val&<>\"");
        xmlWriter.writeCharacters("Hello World!");
        xmlWriter.writeEndElement("tag2", "ns2");
        xmlWriter.writeStartElement("tag3", "ns3");
        xmlWriter.writeAttribute("atr", "val");
        xmlWriter.writeEndElement("tag3", "ns3");
        xmlWriter.writeEndElement("tag1");
        // xmlWriter.writeEndElement(ValueManagerIF.XML_TAG_VALUE);
    }

    private ValueManagerIF getManager(Class type, String name) {

        ValueManagerIF manager = this.service.getManager(type);
        assertNotNull(manager);
        assertSame(this.service.getManager(name), manager);
        assertSame(manager.getValueType(), type);
        assertEquals(manager.getName(), name);
        return manager;
    }

    /**
     * This method tests that the given value manager can parse the value given
     * as string and checks that it can be converted to XML and back resulting
     * in the same value.
     * 
     * @param <V>
     *        is the templated type of the value.
     * @param manager
     *        is the manager of the value type V.
     * @param valueAsString
     *        is the {@link ValueManagerIF#toString(V) "string representation"}
     *        of an example value.
     * @throws Exception
     *         if the test fails.
     */
    private <V> void checkManager(ValueManagerIF<V> manager, String valueAsString)
            throws Exception {

        V value = manager.parse(valueAsString);
        assertEquals(manager.toString(value), valueAsString);
        Document doc = DomUtil.createDocument();
        manager.toXml(new DomXmlWriter(doc), value);
        Element rootElement = doc.getDocumentElement();
        assertEquals(manager.parse(rootElement), value);        
    }

    /**
     * This method does the test.
     * 
     * @throws XmlException
     *         on xml problem.
     * @throws ValueParseException
     *         on parsing problem.
     */
    public void testXml() throws XmlException, ValueParseException {

        /**
         * XMLStreamWriter xmlSerializer =
         * StaxUtil.createXmlStreamWriter(System.out);
         * xmlSerializer.writeStartDocument(); toXml(xmlSerializer);
         * xmlSerializer.writeEndDocument(); xmlSerializer.close();
         */
        ValueManagerIF<Element> manager = getManager(Element.class, XmlValueManager.VALUE_NAME);
        System.out.println(manager.toString());
        Document doc = DomUtil.createDocument();
        XmlWriterIF domWriter = new DomXmlWriter(doc);
        toXml(domWriter);
        Element rootElement = doc.getDocumentElement();
        String xmlString = manager.toString(rootElement);
        System.out.println(xmlString);
        System.out.println("--");

        Element root2Element = manager.parse(xmlString);
        String xmlString2 = manager.toString(root2Element);
        System.out.println(xmlString2);
        System.out.println("--");
        // assertEquals(xmlString, xmlString2);
        StringWriter sw = new StringWriter();
        XmlWriterIF outSerializer = new OutputXmlWriter(sw, null, "UTF-8");
        toXml(outSerializer);
        System.out.println(sw.toString());
        System.out.println("--");
    }

    /**
     * The test for value type "String".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testString() throws Exception {

        ValueManagerIF<String> manager = getManager(String.class, "String");
        checkManager(manager, "Hello World!");
    }

    /**
     * The test method for type "Integer".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testInteger() throws Exception {

        ValueManagerIF<Integer> manager = getManager(Integer.class, "Integer");
        checkManager(manager, "42");
    }

    /**
     * The test method for type "Integer".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testBoolean() throws Exception {

        ValueManagerIF<Boolean> manager = getManager(Boolean.class, "Boolean");
        checkManager(manager, "true");
        checkManager(manager, "false");
    }

    /**
     * The test method for type "Long".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testLong() throws Exception {

        ValueManagerIF<Long> manager = getManager(Long.class, "Long");
        checkManager(manager, "123456789012345678");
    }

    /**
     * The test method for type "Double".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testDouble() throws Exception {

        ValueManagerIF<Double> manager = getManager(Double.class, "Double");
        checkManager(manager, "42.4242424242");
    }

    /**
     * The test method for type "Date".
     * 
     * @throws Exception
     *         if the test failed.
     */
    public void testDate() throws Exception {

        ValueManagerIF<Date> manager = getManager(Date.class, "Date");
        checkManager(manager, "2005-01-01T23:59:00");
    }

}
