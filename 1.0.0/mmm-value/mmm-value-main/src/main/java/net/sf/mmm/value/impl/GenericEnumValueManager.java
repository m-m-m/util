/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.util.value.ValueParseException;
import net.sf.mmm.value.base.BasicValueManager;

/**
 * This is a generic implementation of the
 * {@link net.sf.mmm.value.api.ValueManager} interface used for {@link Enum}
 * types.
 * 
 * @param <E> is the managed value type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericEnumValueManager<E extends Enum<E>> extends BasicValueManager<E> {

  /**
   * The constructor.
   * 
   * @param valueClass
   */
  public GenericEnumValueManager(Class<E> valueClass) {

    super(valueClass);
  }

  /**
   * The constructor.
   * 
   * @param valueClass
   * @param typeName
   */
  public GenericEnumValueManager(Class<E> valueClass, String typeName) {

    super(valueClass, typeName);
  }

  /**
   * {@inheritDoc}
   */
  public E fromString(String valueAsString) throws ValueParseException {

    return Enum.valueOf(getValueClass(), valueAsString);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String toStringNotNull(E value) {
  
    return value.name();
  }
  
}
