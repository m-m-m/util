/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * This is the test-case for the class {@link Property}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PropertyTest {

  @Test
  public void testPlain() {

    String propertyName = "fooBar";
    Property property = new Property(propertyName);
    assertEquals(propertyName, property.getName());
    assertNull(property.getIndex());
    assertNull(property.getKey());
  }

  @Test
  public void testIndexed() {

    int index = 42;
    String propertyName = "fooBar";
    Property property = new Property(propertyName + "[" + index + "]");
    assertEquals(propertyName, property.getName());
    assertNotNull(property.getIndex());
    assertEquals(index, property.getIndex().intValue());
    assertNull(property.getKey());
  }

  @Test
  public void testMapped() {

    String key = "0.key";
    String propertyName = "fooBar";
    Property property = new Property(propertyName + "['" + key + "']");
    assertEquals(propertyName, property.getName());
    assertNull(property.getIndex());
    assertEquals(key, property.getKey());
  }

  @Test
  public void testNegative() {

    try {
      new Property("");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new Property("foo[");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new Property("foo[]");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new Property("foo[bar]");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new Property("foo[']");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }
  }
}
