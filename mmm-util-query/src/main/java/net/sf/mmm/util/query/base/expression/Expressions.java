/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.expression;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Expression;

/**
 * Gives static access to create {@link Expression}s and {@link Argument}s.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class Expressions {

  /**
   * The constructor.
   */
  private Expressions() {
  }

  public static Expression or(Expression... expressions) {

    return combine(Conjunction.OR, expressions);
  }

  public static Expression and(Expression... expressions) {

    return combine(Conjunction.AND, expressions);
  }

  public static Expression combine(Conjunction conjunction, Expression... expressions) {

    return ConjunctionExpression.valueOf(conjunction, expressions);
  }

}
