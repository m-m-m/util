/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import net.sf.mmm.util.pojo.descriptor.base.PojoProperty;

/**
 * This is the test-case for the class {@link PojoProperty}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoPropertyTest {

  @Test
  public void testPlain() {

    String propertyName = "fooBar";
    PojoProperty property = new PojoProperty(propertyName);
    assertEquals(propertyName, property.getName());
    assertNull(property.getIndex());
    assertNull(property.getKey());
  }

  @Test
  public void testIndexed() {

    int index = 42;
    String propertyName = "fooBar";
    PojoProperty property = new PojoProperty(propertyName + "[" + index + "]");
    assertEquals(propertyName, property.getName());
    assertNotNull(property.getIndex());
    assertEquals(index, property.getIndex().intValue());
    assertNull(property.getKey());
  }

  @Test
  public void testMapped() {

    String key = "0.key";
    String propertyName = "fooBar";
    PojoProperty property = new PojoProperty(propertyName + "['" + key + "']");
    assertEquals(propertyName, property.getName());
    assertNull(property.getIndex());
    assertEquals(key, property.getKey());
  }

  @Test
  public void testNegative() {

    try {
      new PojoProperty("");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new PojoProperty("foo[");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new PojoProperty("foo[]");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new PojoProperty("foo[bar]");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }

    try {
      new PojoProperty("foo[']");
      fail("Exception expected");
    } catch (RuntimeException e) {
    }
  }
}
