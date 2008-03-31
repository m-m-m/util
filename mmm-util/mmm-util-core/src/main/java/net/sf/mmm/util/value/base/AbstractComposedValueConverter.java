/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.component.AbstractLoggable;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is the abstract base implementation of the
 * {@link ComposedValueConverter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComposedValueConverter extends AbstractLoggable implements
    ComposedValueConverter {

  /**
   * The constructor.
   */
  public AbstractComposedValueConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public final Class<Object> getTargetType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object convert(Object value, Object valueSource, Class<? extends Object> targetClass) {

    return convert(value, valueSource, targetClass, targetClass);
  }

}
