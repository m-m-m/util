/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;

import net.sf.mmm.util.value.base.AbstractComposedValueConverter;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.value.api.ComposedValueConverter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComposedGenericValueConverter extends AbstractComposedValueConverter {

  /**
   * {@inheritDoc}
   */
  public Object convert(Object value, Object valueSource, Class<? extends Object> targetClass,
      Type targetType) {

    if (value == null) {
      return null;
    }
    Class<?> sourceClass = value.getClass();

    // TODO Auto-generated method stub
    return null;
  }

}
