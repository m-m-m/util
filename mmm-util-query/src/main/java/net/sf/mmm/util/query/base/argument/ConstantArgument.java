/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;

/**
 * This is the {@link #isConstant() constant} implementation of {@link Argument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstantArgument<V> implements AbstractArgument<V> {

  /** The singleton {@link ConstantArgument} instance for the {@code null} literal. */
  @SuppressWarnings("rawtypes")
  public static final ConstantArgument NULL = new ConstantArgument<>(null);

  private final V value;

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantArgument(V value) {
    super();
    this.value = value;
  }

  /**
   * @return the {@link Path} of this builder. Will be {@code null} if {@link #getValue() value} is NOT {@code null} and
   *         vice versa.
   */
  public Path<V> getPath() {

    return null;
  }

  /**
   * @return the literal value of this builder. Will be {@code null} if {@link #getPath() path} is NOT {@code null} and
   *         vice versa.
   */
  public V getValue() {

    return this.value;
  }

}
