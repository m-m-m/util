/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is the abstract base-implementation of the {@link ValueConverter}
 * interface.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueConverter<SOURCE, TARGET> implements
    ValueConverter<SOURCE, TARGET> {

  /**
   * The constructor.
   */
  public AbstractValueConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public TARGET convert(SOURCE value, Object valueSource, Class<? extends TARGET> targetClass) {

    return convert(value, valueSource, targetClass, targetClass);
  }

}
