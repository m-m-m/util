/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;

/**
 * This is the interface for a builder of an {@link Expression} checking one argument or comparing two arguments.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface Argument<V> {

  /**
   * @return the {@link Path} of this builder. Will be {@code null} if {@link #getValue() value} is NOT {@code null} and
   *         vice versa.
   */
  Path<V> getPath();

  /**
   * @return the literal value of this {@link Argument}. Will be {@code null} if {@link #getPath() path} is NOT
   *         {@code null} and vice versa.
   */
  V getValue();

  /**
   * @return the value of this {@link Argument}.
   */
  default V evaluate() {

    if (isConstant()) {
      return getValue();
    } else {
      return getPath().getValue();
    }
  }

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this == value}.
   */
  Expression eq(V value);

  /**
   * @param property the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this == property}.
   */
  Expression eq(Path<V> property);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this != value}.
   */
  Expression neq(V value);

  /**
   * @param property the {@link Path path} to compare.
   * @return an {@link Expression} for {@code this != property}.
   */
  Expression neq(Path<V> property);

  /**
   * Create a {@code this is not null} expression
   *
   * @return this is not null
   */
  Expression isNotNull();

  /**
   * Create a {@code this is null} expression
   *
   * @return this is null
   */
  Expression isNull();

  /**
   * @return {@code true} if this is a literal {@link #getValue() value} argument, {@code false} otherwise.
   */
  default boolean isConstant() {

    return (getPath() == null);
  }

}
