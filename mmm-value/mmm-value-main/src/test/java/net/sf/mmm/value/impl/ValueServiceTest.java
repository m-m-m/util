/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import java.io.StringWriter;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.util.xml.DomUtil;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;
import net.sf.mmm.util.xml.impl.DomXmlWriter;
import net.sf.mmm.util.xml.impl.OutputXmlWriter;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.api.ValueManager;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueService;
import net.sf.mmm.value.impl.ValueServiceImpl;
import net.sf.mmm.value.impl.type.DateValueManager;
import net.sf.mmm.value.impl.type.XmlValueManager;

/**
 * This is the test case for {@link net.sf.mmm.value.impl.ValueServiceImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ValueServiceTest extends TestCase {

  /** the value service instance */
  private ValueService service;

  /**
   * The constructor.
   */
  public ValueServiceTest() {

    super();
    this.service = new StaticValueServiceImpl();
  }

  private ValueManager getManager(Class type, String name) {

    ValueManager manager = this.service.getManager(type);
    assertNotNull(manager);
    assertSame(this.service.getManager(name), manager);
    assertSame(manager.getValueClass(), type);
    assertEquals(manager.getName(), name);
    return manager;
  }

  /**
   * This method tests that the given value manager can parse the value given as
   * string and checks that it can be converted to XML and back resulting in the
   * same value.
   * 
   * @param <V> is the templated type of the value.
   * @param manager is the manager of the value type V.
   * @param valueAsString is the
   *        {@link ValueManager#toString(V) "string representation"} of an
   *        example value.
   * @throws Exception if the test fails.
   */
  private <V> void checkManager(ValueManager<V> manager, String valueAsString) throws Exception {

    V value = manager.parse(valueAsString);
    assertEquals(manager.toString(value), valueAsString);
    Document doc = DomUtil.createDocument();
    manager.toXml(new DomXmlWriter(doc), value);
    Element rootElement = doc.getDocumentElement();
    V parsedValue = manager.parse(rootElement);
    assertTrue("parsed " + manager.getName() + " value differs", manager
        .isEqual(value, parsedValue));
  }

  /**
   * The test for value type "String".
   */
  @Test
  public void testString() throws Exception {

    ValueManager<String> manager = getManager(String.class, "String");
    checkManager(manager, "Hello World!");
  }

  /**
   * The test method for type "Integer".
   */
  @Test
  public void testInteger() throws Exception {

    ValueManager<Integer> manager = getManager(Integer.class, "Integer");
    checkManager(manager, "42");
  }

  /**
   * The test method for type "Integer".
   */
  @Test
  public void testBoolean() throws Exception {

    ValueManager<Boolean> manager = getManager(Boolean.class, "Boolean");
    checkManager(manager, "true");
    checkManager(manager, "false");
  }

  /**
   * The test method for type "Long".
   */
  @Test
  public void testLong() throws Exception {

    ValueManager<Long> manager = getManager(Long.class, "Long");
    checkManager(manager, "123456789012345678");
  }

  /**
   * The test method for type "Double".
   */
  @Test
  public void testDouble() throws Exception {

    ValueManager<Double> manager = getManager(Double.class, "Double");
    checkManager(manager, "42.4242424242");
  }

  /**
   * The test method for type "Date".
   */
  @Test
  public void testDate() throws Exception {

    ValueManager<Date> manager = getManager(Date.class, "Date");
    checkManager(manager, "2005-01-01T23:59:00Z");
  }

  /**
   * The test method for type "Xml".
   */
  @Test
  public void testXml() throws Exception {

    ValueManager<Element> manager = getManager(Element.class, XmlValueManager.VALUE_NAME);
    checkManager(manager,
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><tag attribute=\"value\">text</tag></root>");
  }

}
