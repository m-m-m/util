/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to <code>double[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToArrayOfDouble extends AbstractConverterToArray<double[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfDouble() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<double[]> getTargetType() {

    return double[].class;
  }

}
