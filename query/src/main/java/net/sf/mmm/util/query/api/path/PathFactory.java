/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.bean.api.entity.EntityBean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.ReadableSetProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableStringProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;
import net.sf.mmm.util.property.api.math.ReadableNumberProperty;
import net.sf.mmm.util.property.api.util.ReadableListProperty;
import net.sf.mmm.util.property.api.util.ReadableMapProperty;

/**
 * This is the abstract interface for a factory to {@link #to(ReadableBooleanProperty) create} instances of {@link Path}
 * and its sub-types. An instance of {@link PathFactory} is either a {@link Path} itself or a {@link PathRoot}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract interface PathFactory {

  /**
   * @param <V> the generic type of the {@link ReadableProperty#getValue() property value}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <V> Path<V> to(ReadableProperty<V> property);

  /**
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  BooleanPath to(ReadableBooleanProperty property);

  /**
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  StringPath to(ReadableStringProperty property);

  /**
   * @param <V> the generic type of the {@link ReadableProperty#getValue() property value}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <V extends Number & Comparable<?>> NumberPath<V> to(ReadableNumberProperty<V> property);

  /**
   * @param <E> the generic type of the {@link java.util.List#get(int) list elements}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <E> ListPath<E> to(ReadableListProperty<E> property);

  /**
   * @param <E> the generic type of the {@link java.util.Set#contains(Object) set elements}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <E> SetPath<E> to(ReadableSetProperty<E> property);

  /**
   * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
   * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <K, V> MapPath<K, V> to(ReadableMapProperty<K, V> property);

  /**
   * @param <V> the generic type of the {@link ReadableProperty#getValue() property value}.
   * @param property the {@link ReadableProperty property} to append.
   * @return the new {@link Path} resulting from joining the given {@link ReadableProperty property}.
   */
  <V extends EntityBean> Path<V> to(LinkProperty<V> property);

}
