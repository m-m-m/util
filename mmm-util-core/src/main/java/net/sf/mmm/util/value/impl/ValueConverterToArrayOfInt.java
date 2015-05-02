/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to <code>int[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class ValueConverterToArrayOfInt extends AbstractConverterToArray<int[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfInt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<int[]> getTargetType() {

    return int[].class;
  }

}
