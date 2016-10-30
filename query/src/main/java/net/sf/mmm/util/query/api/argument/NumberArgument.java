/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.value.api.Range;

/**
 * Extends {@link Argument} to build an {@link Expression} for {@link Number} value(s).
 *
 * @param <V> the generic type of the value to check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface NumberArgument<V extends Number & Comparable<?>> extends ComparableArgument<V> {

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this >= value}.
   */
  Expression geq(V value);

  /**
   * @param path the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this >= path}.
   */
  Expression geq(Path<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this > value}.
   */
  Expression gt(V value);

  /**
   * @param path the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this > path}.
   */
  Expression gt(Path<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this < value}.
   */
  Expression lt(V value);

  /**
   * @param path the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this < path}.
   */
  Expression lt(Path<V> path);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this <= value}.
   */
  Expression leq(V value);

  /**
   * @param path the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this <= path}.
   */
  Expression leq(Path<V> path);

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
