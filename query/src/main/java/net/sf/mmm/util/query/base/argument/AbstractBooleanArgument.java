/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.BooleanArgument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.base.expression.SqlOperator;

/**
 * The abstract base implementation of {@link BooleanArgument}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract interface AbstractBooleanArgument extends AbstractComparableArgument<Boolean>, BooleanArgument {

  @Override
  default Expression isTrue() {

    return exp(SqlOperator.EQUAL, Boolean.TRUE);
  }

  @Override
  default Expression isFalse() {

    return exp(SqlOperator.EQUAL, Boolean.FALSE);
  }

}
