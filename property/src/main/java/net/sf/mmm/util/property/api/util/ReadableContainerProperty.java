/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.math.ReadableIntegerProperty;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link java.util.Collection} or {@link java.util.Map}.
 *
 * @param <V> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface ReadableContainerProperty<V> extends ReadableProperty<V> {

  /**
   * @return the result of {@link #getValue()} but an empty container ({@link java.util.Collection} or
   *         {@link java.util.Map}) instead of {@code null}.
   */
  V getValueNotNull();

  /**
   * @return the {@link java.util.Collection#size()} of the {@link #getValueNotNull() container}.
   */
  int size();

  /**
   * @return the {@link java.util.Collection#isEmpty() empty state} of the {@link #getValueNotNull() container}.
   */
  boolean isEmpty();

  /**
   * @return an {@link ReadableIntegerProperty} that represents the {@link java.util.Collection#size()} property of the
   *         {@link #getValueNotNull() container}.
   */
  ReadableIntegerProperty sizeProperty();

  /**
   * @return an {@link ReadableBooleanProperty} that represents the {@link java.util.Collection#isEmpty() empty}
   *         property of the {@link #getValueNotNull() container}.
   */
  ReadableBooleanProperty emptyProperty();

}
