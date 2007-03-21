/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link StringValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringValueTest extends AbstractGenericValueTest {

  /**
   * The constructor.
   */
  public StringValueTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericValue convert(Object plainValue) {

    String s;
    if (plainValue == null) {
      s = null;
    } else if (plainValue instanceof Class) {
      s = ((Class) plainValue).getName();
    } else {
      s = plainValue.toString();
    }
    return new StringValue(s);
  }

}
