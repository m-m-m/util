/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.ValueException;

/**
 * This is an implementation of {@link net.sf.mmm.util.value.api.ValueConverter} that returns the unmodified
 * value (identity conversion).
 * 
 * @param <V> is the generic type of the value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValueConverterIdentity<V> extends AbstractSimpleValueConverter<V, V> {

  /** @see #getSourceType() */
  private final Class<V> valueClass;

  /**
   * The constructor.
   * 
   * @param valueClass is the {@link Class} reflecting the value type.
   */
  public ValueConverterIdentity(Class<V> valueClass) {

    super();
    this.valueClass = valueClass;
  }

  /**
   * {@inheritDoc}
   */
  public Class<V> getSourceType() {

    return this.valueClass;
  }

  /**
   * {@inheritDoc}
   */
  public Class<V> getTargetType() {

    return this.valueClass;
  }

  /**
   * {@inheritDoc}
   */
  public <T extends V> T convert(V value, Object valueSource, Class<T> targetClass) throws ValueException {

    return targetClass.cast(value);
  }

}
