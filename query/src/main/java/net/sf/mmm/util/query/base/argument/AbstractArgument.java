/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Arrays;
import java.util.Collection;

import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.base.expression.SingleExpression;
import net.sf.mmm.util.query.base.expression.SqlOperator;

/**
 * This is abstract base implementation of {@link Argument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface AbstractArgument<V> extends Argument<V> {

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link SingleExpression#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  default Expression exp(SqlOperator<? super V, ? super V> operator, V arg2) {

    return SingleExpression.valueOf(this, operator, new ConstantArgument<>(arg2));
  }

  /**
   * @param <R> the generic type of the {@link SingleExpression#getArg2() second argument} (right hand).
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link SingleExpression#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  default <R> Expression expRight(SqlOperator<? super V, ? super R> operator, R arg2) {

    return SingleExpression.valueOf(this, operator, new ConstantArgument<>(arg2));
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link SingleExpression#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  default Expression exp(SqlOperator<? super V, ? super V> operator, Path<V> arg2) {

    return SingleExpression.valueOf(this, operator, arg2);
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link SingleExpression#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  default Expression exp(SqlOperator<? super V, Void> operator) {

    return SingleExpression.valueOf(this, operator, null);
  }

  /**
   * @param operator the {@link SqlOperator}.
   * @param arg2 the {@link SingleExpression#getArg2() second argument}.
   * @return the resulting {@link Expression}.
   */
  default Expression exp(SqlOperator<? super V, ? super V> operator, ConstantArgument<V> arg2) {

    return SingleExpression.valueOf(this, operator, arg2);
  }

  @Override
  default Expression eq(V arg2) {

    return exp(SqlOperator.EQUAL, arg2);
  }

  @Override
  default Expression eq(Path<V> arg2) {

    return exp(SqlOperator.EQUAL, arg2);
  }

  @Override
  default Expression neq(V arg2) {

    return exp(SqlOperator.NOT_EQUAL, arg2);
  }

  @Override
  default Expression neq(Path<V> arg2) {

    return exp(SqlOperator.NOT_EQUAL, arg2);
  }

  @Override
  default Expression isNotNull() {

    return exp(SqlOperator.NOT_EQUAL, ConstantArgument.NULL);
  }

  @Override
  default Expression isNull() {

    return exp(SqlOperator.EQUAL, ConstantArgument.NULL);
  }

  @Override
  default Expression in(Collection<? extends V> values) {

    return expRight(SqlOperator.IN, values);
  }

  @SuppressWarnings("unchecked")
  @Override
  default Expression in(V... values) {

    return in(Arrays.asList(values));
  }

  @Override
  default Expression notIn(Collection<? extends V> values) {

    return expRight(SqlOperator.NOT_IN, values);
  }

  @SuppressWarnings("unchecked")
  @Override
  default Expression notIn(V... values) {

    return notIn(Arrays.asList(values));
  }

}
