/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to <code>short[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ValueConverterToArrayOfShort extends AbstractConverterToArray<short[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfShort() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<short[]> getTargetType() {

    return short[].class;
  }

}
