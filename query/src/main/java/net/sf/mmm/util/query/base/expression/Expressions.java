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
 * @since 8.4.0
 */
public class Expressions {

  /**
   * The constructor.
   */
  private Expressions() {
  }

  /**
   * @param expressions the {@link Expression}s to {@link #combine(Conjunction, Expression...)} using
   *        {@link Conjunction#OR}.
   * @return an {@link Expression} that {@link Expression#evaluate() evaluates} to {@code true} if any of the given
   *         {@link Expression}s does.
   */
  public static Expression or(Expression... expressions) {

    return combine(Conjunction.OR, expressions);
  }

  /**
   * @param expressions the {@link Expression}s to {@link #combine(Conjunction, Expression...)} using
   *        {@link Conjunction#AND}.
   * @return an {@link Expression} that {@link Expression#evaluate() evaluates} to {@code true} if all of the given
   *         {@link Expression}s do.
   */
  public static Expression and(Expression... expressions) {

    return combine(Conjunction.AND, expressions);
  }

  /**
   * @see Conjunction#eval(boolean...)
   * @param conjunction the {@link Conjunction} used to combine the given {@link Expression}s.
   * @param expressions the {@link Expression}s to combine using the given {@link Conjunction}.
   * @return an {@link Expression} that combines the given {@link Expression}s with the given {@link Conjunction}.
   */
  public static Expression combine(Conjunction conjunction, Expression... expressions) {

    return ConjunctionExpression.valueOf(conjunction, expressions);
  }

}
