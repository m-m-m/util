/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableNumberProperty;
import net.sf.mmm.util.property.api.lang.ReadableStringProperty;

/**
 * This is the abstract interface for a factory to {@link #to(ReadableBooleanProperty) create} instances of {@link Path}
 * and its sub-types. An instance of {@link PathFactory} is either a {@link Path} itself or a {@link PathRoot}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface PathFactory {

  /**
   * @param <V> the generic type of the {@link ReadableProperty#getValue() property value}.
   * @param property the {@link ReadableProperty} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <V> Path<V> to(ReadableProperty<V> property);

  /**
   * @param property the {@link ReadableBooleanProperty} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  BooleanPath to(ReadableBooleanProperty property);

  /**
   * @param property the {@link ReadableStringProperty} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  StringPath to(ReadableStringProperty property);

  /**
   * @param <V> the generic type of the {@link ReadableProperty#getValue() property value}.
   * @param property the {@link ReadableNumberProperty} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <V extends Number & Comparable<?>> NumberPath<V> to(ReadableNumberProperty<V> property);

}
