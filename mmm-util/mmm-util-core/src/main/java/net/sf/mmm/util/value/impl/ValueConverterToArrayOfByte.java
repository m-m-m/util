/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to <code>byte[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ValueConverterToArrayOfByte extends AbstractConverterToArray<byte[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfByte() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<byte[]> getTargetType() {

    return byte[].class;
  }

}
