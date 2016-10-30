/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.Collection;

import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Collection}.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained in the collection}.
 * @param <V> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface WritableCollectionProperty<E, V extends Collection<E>>
    extends ReadableCollectionProperty<E, V>, WritableProperty<V> {

  /**
   * @return the result of {@link #getValue()} but including lazy initialization with an empty {@link Collection} to
   *         prevent {@code null} as result.
   */
  V getOrCreateValue();

}
