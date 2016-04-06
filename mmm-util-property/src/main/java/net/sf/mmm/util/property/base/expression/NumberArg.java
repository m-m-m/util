/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.expression.NumberArgument;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the implementation of {@link NumberArgument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class NumberArg<V extends Number & Comparable<?>> extends ComparableArg<V> implements NumberArgument<V> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public NumberArg(V value) {
    super(value);
  }

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public NumberArg(PropertyPath<V> path) {
    super(path);
  }

  @Override
  public Expression geq(V value) {

    return exp(SqlOperator.GREATER_OR_EQUAL, value);
  }

  @Override
  public Expression geq(PropertyPath<V> path) {

    return exp(SqlOperator.GREATER_OR_EQUAL, path);
  }

  @Override
  public Expression gt(V value) {

    return exp(SqlOperator.GREATER_THAN, value);
  }

  @Override
  public Expression gt(PropertyPath<V> path) {

    return exp(SqlOperator.GREATER_THAN, path);
  }

  @Override
  public Expression leq(V value) {

    return exp(SqlOperator.LESS_OR_EQUAL, value);
  }

  @Override
  public Expression leq(PropertyPath<V> path) {

    return exp(SqlOperator.LESS_OR_EQUAL, path);
  }

  @Override
  public Expression lt(V value) {

    return exp(SqlOperator.LESS_THAN, value);
  }

  @Override
  public Expression lt(PropertyPath<V> path) {

    return exp(SqlOperator.LESS_THAN, path);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Expression between(Range<V> range) {

    Range r = range;
    return expRight(SqlOperator.BETWEEN, (Range<Number>) r);
  }

}
