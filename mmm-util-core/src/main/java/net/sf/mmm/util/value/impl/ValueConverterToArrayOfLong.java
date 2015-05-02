/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to <code>long[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class ValueConverterToArrayOfLong extends AbstractConverterToArray<long[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfLong() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<long[]> getTargetType() {

    return long[].class;
  }

}
