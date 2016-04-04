/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.property.api.expression.Argument;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the implementation of {@link Argument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class Arg<V> implements Argument<V> {

  /** The singleton {@link Arg} instance for the {@code null} literal. */
  @SuppressWarnings("rawtypes")
  public static final Arg NULL = new Arg<>(null);

  private final V value;

  private final PropertyPath<V> path;

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public Arg(V value) {
    super();
    this.value = value;
    this.path = null;
  }

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public Arg(PropertyPath<V> path) {
    super();
    this.value = null;
    this.path = path;
  }

  /**
   * @return the {@link PropertyPath} of this builder. Will be {@code null} if {@link #getValue() value} is NOT
   *         {@code null} and vice versa.
   */
  public PropertyPath<V> getPath() {

    return this.path;
  }

  /**
   * @return the literal value of this builder. Will be {@code null} if {@link #getPath() path} is NOT {@code null} and
   *         vice versa.
   */
  public V getValue() {

    return this.value;
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link ExpressionImpl#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  protected Expression exp(SqlOperator<? super V, ? super V> operator, V arg2) {

    return new ExpressionImpl<>(this, operator, new Arg<>(arg2));
  }

  /**
   * @param <R> the generic type of the {@link ExpressionImpl#getArg2() second argument} (right hand).
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link ExpressionImpl#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  protected <R> Expression expRight(SqlOperator<? super V, ? super R> operator, R arg2) {

    return new ExpressionImpl<>(this, operator, new Arg<>(arg2));
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link ExpressionImpl#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  protected Expression exp(SqlOperator<? super V, ? super V> operator, PropertyPath<V> arg2) {

    return new ExpressionImpl<>(this, operator, new Arg<>(arg2));
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link ExpressionImpl#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  protected Expression exp(SqlOperator<? super V, ? super V> operator, Arg<V> arg2) {

    return new ExpressionImpl<>(this, operator, arg2);
  }

  @Override
  public Expression eq(V arg2) {

    return exp(SqlOperator.EQUAL, arg2);
  }

  @Override
  public Expression eq(PropertyPath<V> arg2) {

    return exp(SqlOperator.EQUAL, arg2);
  }

  @Override
  public Expression neq(V arg2) {

    return exp(SqlOperator.NOT_EQUAL, arg2);
  }

  @Override
  public Expression neq(PropertyPath<V> arg2) {

    return exp(SqlOperator.NOT_EQUAL, arg2);
  }

  @Override
  public Expression isNotNull() {

    return exp(SqlOperator.NOT_EQUAL, NULL);
  }

  @Override
  public Expression isNull() {

    return exp(SqlOperator.EQUAL, NULL);
  }

  /**
   * @return the value of this {@link Argument}.
   */
  public V evaluate() {

    if (this.path != null) {
      return this.path.getValue();
    }
    return this.value;
  }

  @Override
  public boolean isStatic() {

    return (this.path == null);
  }

}
