/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link ObjectValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ImmutableObjectValueTest extends AbstractGenericValueTest {

  /**
   * The constructor.
   */
  public ImmutableObjectValueTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericValue convert(Object plainValue) {

    return new ObjectValue(plainValue);
  }

  @Test
  public void testConversion() {

    int i = 42;
    String s = Integer.toString(i);
    GenericValue value = convert(s);
    assertEquals(s, value.getString());
    assertEquals(i, value.getInteger());
    assertEquals(i, value.getLong());
    assertEquals(i, value.getDouble(), 0);
    assertEquals(i, value.getNumber().doubleValue(), 0);
    assertEquals(i, value.getValue(Float.class).doubleValue(), 0);
    assertEquals(i, value.getValue(Byte.class).intValue());
    assertEquals(i, value.getValue(Short.class).intValue());

    boolean b = true;
    s = Boolean.toString(b);
    value = convert(s);
    assertEquals(s, value.getString());
    assertEquals(b, value.getBoolean());
  }

}
