/* $ Id: $ */
package net.sf.mmm.value.impl;

import java.io.StringWriter;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueServiceIF;
import net.sf.mmm.value.impl.ValueService;
import net.sf.mmm.value.impl.type.XmlValueManager;
import net.sf.mmm.xml.api.XmlException;
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
    ValueService serviceImpl = new ValueService();
    serviceImpl.initialize();
    this.service = serviceImpl;
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
   * This method tests that the given value manager can parse the value given as
   * string and checks that it can be converted to XML and back resulting in the
   * same value.
   * 
   * @param <V>
   *        is the templated type of the value.
   * @param manager
   *        is the manager of the value type V.
   * @param valueAsString
   *        is the {@link ValueManagerIF#toString(V) "string representation"} of
   *        an example value.
   * @throws Exception
   *         if the test fails.
   */
  private <V> void checkManager(ValueManagerIF<V> manager, String valueAsString) throws Exception {

    V value = manager.parse(valueAsString);
    assertEquals(manager.toString(value), valueAsString);
    Document doc = DomUtil.createDocument();
    manager.toXml(new DomXmlWriter(doc), value);
    Element rootElement = doc.getDocumentElement();    
    assertEquals(manager.parse(rootElement), value);
  }

  /**
   * The test for value type "String".
   */
  @Test
  public void testString() throws Exception {

    ValueManagerIF<String> manager = getManager(String.class, "String");
    checkManager(manager, "Hello World!");
  }

  /**
   * The test method for type "Integer".
   */
  @Test
  public void testInteger() throws Exception {

    ValueManagerIF<Integer> manager = getManager(Integer.class, "Integer");
    checkManager(manager, "42");
  }

  /**
   * The test method for type "Integer".
   */
  @Test
  public void testBoolean() throws Exception {

    ValueManagerIF<Boolean> manager = getManager(Boolean.class, "Boolean");
    checkManager(manager, "true");
    checkManager(manager, "false");
  }

  /**
   * The test method for type "Long".
   */
  @Test
  public void testLong() throws Exception {

    ValueManagerIF<Long> manager = getManager(Long.class, "Long");
    checkManager(manager, "123456789012345678");
  }

  /**
   * The test method for type "Double".
   */
  @Test
  public void testDouble() throws Exception {

    ValueManagerIF<Double> manager = getManager(Double.class, "Double");
    checkManager(manager, "42.4242424242");
  }

  /**
   * The test method for type "Date".
   */
  @Test
  public void testDate() throws Exception {

    ValueManagerIF<Date> manager = getManager(Date.class, "Date");
    checkManager(manager, "2005-01-01T23:59:00");
  }

  /**
   * The test method for type "Xml".
   */
  @Test
  public void testXml() throws Exception {

    ValueManagerIF<Element> manager = getManager(Element.class, XmlValueManager.VALUE_NAME);
    checkManager(manager,
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><tag attribute=\"value\">text</tag></root>");
  }

}
