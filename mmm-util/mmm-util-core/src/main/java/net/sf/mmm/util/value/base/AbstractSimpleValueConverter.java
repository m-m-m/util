/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is an abstract base-implementation of the {@link ValueConverter}
 * interface that simply works with {@link Class} rather than
 * {@link GenericType}.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class AbstractSimpleValueConverter<SOURCE, TARGET> extends AbstractLoggable
    implements ValueConverter<SOURCE, TARGET> {

  /**
   * The constructor.
   */
  public AbstractSimpleValueConverter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final TARGET convert(SOURCE value, Object valueSource,
      GenericType<? extends TARGET> targetType) {

    return convert(value, valueSource, targetType.getRetrievalClass());
  }

}
