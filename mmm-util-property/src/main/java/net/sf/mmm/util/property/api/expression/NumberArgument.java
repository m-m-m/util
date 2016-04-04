/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.expression;

import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.value.api.Range;

/**
 * Extends {@link Argument} to build an {@link Expression} for {@link Number} value(s).
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface NumberArgument<V extends Number & Comparable<?>> extends ComparableArgument<V> {

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this >= value}.
   */
  Expression geq(V value);

  /**
   * @param path the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this >= path}.
   */
  Expression geq(PropertyPath<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this > value}.
   */
  Expression gt(V value);

  /**
   * @param path the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this > path}.
   */
  Expression gt(PropertyPath<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this < value}.
   */
  Expression lt(V value);

  /**
   * @param path the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this < path}.
   */
  Expression lt(PropertyPath<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this <= value}.
   */
  Expression leq(V value);

  /**
   * @param path the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this <= path}.
   */
  Expression leq(PropertyPath<V> path);

  /**
   * @param min - see {@link Range#getMin()}.
   * @param max - see {@link Range#getMax()}.
   * @return an {@link Expression} for {@code min <= this <= max}.
   */
  default Expression between(V min, V max) {

    return between(new Range<>(min, max));
  }

  /**
   * @param range the constant {@link Range} to match.
   * @return an {@link Expression} for {@code min <= this <= max}.
   */
  Expression between(Range<V> range);

}
