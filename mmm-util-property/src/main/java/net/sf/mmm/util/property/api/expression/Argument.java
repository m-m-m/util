/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.expression;

import net.sf.mmm.util.property.api.path.PropertyPath;

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
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this == value}.
   */
  Expression eq(V value);

  /**
   * @param property the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this == property}.
   */
  Expression eq(PropertyPath<V> property);

  /**
   * @param value the value to compare.
   * @return an {@link Expression} for {@code this != value}.
   */
  Expression neq(V value);

  /**
   * @param property the {@link PropertyPath path} to compare.
   * @return an {@link Expression} for {@code this != property}.
   */
  Expression neq(PropertyPath<V> property);

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
   * @return {@code true} if this is a literal value argument, {@code false} otherwise.
   */
  boolean isConstant();

}
