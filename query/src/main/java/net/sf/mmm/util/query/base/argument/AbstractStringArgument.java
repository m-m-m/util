/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.StringArgument;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.base.expression.SqlOperator;
import net.sf.mmm.util.query.base.expression.SqlOperator.SqlOperatorLike;

/**
 * The abstract base implementation of {@link StringArgument}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract interface AbstractStringArgument extends AbstractComparableArgument<String>, StringArgument {

  @Override
  default Expression like(String pattern) {

    return exp(SqlOperator.LIKE, pattern);
  }

  @Override
  default Expression like(String pattern, char escape) {

    return exp(new SqlOperatorLike(true, escape), pattern);
  }

}
