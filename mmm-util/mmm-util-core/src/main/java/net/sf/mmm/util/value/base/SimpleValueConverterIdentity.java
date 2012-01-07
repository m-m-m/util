/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.SimpleValueConverter;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is an implementation of {@link SimpleValueConverter} that returns the
 * unmodified value (identity conversion).
 * 
 * @param <V> is the generic type of the value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class SimpleValueConverterIdentity<V> implements SimpleValueConverter<V, V> {

  /** @see #getInstance() */
  @SuppressWarnings("rawtypes")
  private static final SimpleValueConverterIdentity INSTANCE = new SimpleValueConverterIdentity();

  /**
   * The constructor.
   */
  public SimpleValueConverterIdentity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public <T extends V> T convert(V value, Object valueSource, Class<T> targetClass)
      throws ValueException {

    return targetClass.cast(value);
  }

  /**
   * @param <V> is the generic type of the value.
   * 
   * @return the instance
   */
  public static <V> SimpleValueConverterIdentity<V> getInstance() {

    return INSTANCE;
  }

}
