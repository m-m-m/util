/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.NumberArgument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.base.expression.SqlOperator;
import net.sf.mmm.util.value.api.Range;

/**
 * The abstract base implementation of {@link NumberArgument}.
 *
 * @param <V> the generic type of the value to check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractNumberArgument<V extends Number & Comparable<?>>
    extends AbstractComparableArgument<V>, NumberArgument<V> {

  @Override
  default Expression geq(V value) {

    return exp(SqlOperator.GREATER_OR_EQUAL, value);
  }

  @Override
  default Expression geq(Path<V> path) {

    return exp(SqlOperator.GREATER_OR_EQUAL, path);
  }

  @Override
  default Expression gt(V value) {

    return exp(SqlOperator.GREATER_THAN, value);
  }

  @Override
  default Expression gt(Path<V> path) {

    return exp(SqlOperator.GREATER_THAN, path);
  }

  @Override
  default Expression leq(V value) {

    return exp(SqlOperator.LESS_OR_EQUAL, value);
  }

  @Override
  default Expression leq(Path<V> path) {

    return exp(SqlOperator.LESS_OR_EQUAL, path);
  }

  @Override
  default Expression lt(V value) {

    return exp(SqlOperator.LESS_THAN, value);
  }

  @Override
  default Expression lt(Path<V> path) {

    return exp(SqlOperator.LESS_THAN, path);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  default Expression between(Range<V> range) {

    Range r = range;
    return expRight(SqlOperator.BETWEEN, (Range<Number>) r);
  }

}
