/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the test-case for testing the class {@link ImmutableObjectValue}.
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

    return new ImmutableObjectValue(plainValue);
  }

}
