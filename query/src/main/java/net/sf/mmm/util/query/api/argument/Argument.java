/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import java.util.Collection;

import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;

/**
 * This is the interface for a builder of an {@link Expression} checking one argument or comparing two arguments.
 *
 * @param <V> the generic type of the value to check by the {@link Expression} to build.
 *
 * @see net.sf.mmm.util.query.base.argument.Args
 * @see net.sf.mmm.util.query.api.path.Path
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface Argument<V> {

  /**
   * @return the {@link Path} of this builder. Will be {@code null} if {@link #getValue() value} is NOT {@code null} and
   *         vice versa.
   */
  Path<V> getValuePath();

  /**
   * @return the literal value of this {@link Argument}. Will be {@code null} if {@link #getValuePath() path} is NOT
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
      return getValuePath().getValue();
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
   * @return an {@link Expression} for {@code this IS NULL}.
   */
  Expression isNull();

  /**
   * @return an {@link Expression} for {@code this IS NOT NULL}.
   */
  Expression isNotNull();

  /**
   * @param values the {@link Collection} with the values the {@link Argument} shall be
   *        {@link Collection#contains(Object) contained} in.
   * @return an {@link Expression} for {@code this IN (values)}.
   */
  Expression in(Collection<? extends V> values);

  /**
   * @param values the array with the values the {@link Argument} shall be {@link Collection#contains(Object) contained}
   *        in.
   * @return an {@link Expression} for {@code this IN (values)}.
   */
  @SuppressWarnings("unchecked")
  Expression in(V... values);

  /**
   * @param values the {@link Collection} with the values the {@link Argument} shall NOT be
   *        {@link Collection#contains(Object) contained} in.
   * @return an {@link Expression} for {@code this NOT IN (values)}.
   */
  Expression notIn(Collection<? extends V> values);

  /**
   * @param values the array with the values the {@link Argument} shall NOT be {@link Collection#contains(Object)
   *        contained} in.
   * @return an {@link Expression} for {@code this NOT IN (values)}.
   */
  @SuppressWarnings("unchecked")
  Expression notIn(V... values);

  /**
   * @return {@code true} if this is a literal {@link #getValue() value} {@link Argument}, {@code false} otherwise.
   */
  default boolean isConstant() {

    return (getValuePath() == null);
  }

}
