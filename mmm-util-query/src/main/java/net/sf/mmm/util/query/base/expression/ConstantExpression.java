/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.expression;

import java.util.Collection;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.expression.Bracketing;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.expression.ExpressionFormatter;

/**
 * This is an implementation of {@link Expression} that is constant and will always {@link #evaluate() evaluate} to a
 * {@link #isConstant() static} (fixed) value.
 *
 * @see #TRUE
 * @see #FALSE
 * @see #valueOf(boolean)
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstantExpression implements Expression {

  /** {@link ConstantExpression} that always {@link #evaluate() evaluates} to {@code true}. */
  public static final ConstantExpression TRUE = new ConstantExpression();

  /** {@link ConstantExpression} that always {@link #evaluate() evaluates} to {@code false}. */
  public static final ConstantExpression FALSE = new ConstantExpression();

  /**
   * The constructor.
   */
  private ConstantExpression() {
    super();
  }

  @Override
  public boolean evaluate() {

    return (this == TRUE);
  }

  @Override
  public Expression negate() {

    return valueOf(!evaluate());
  }

  @Override
  public Expression combine(Conjunction conjunction, Collection<Expression> expressions) {

    return ConjunctionExpression.valueOf(this, conjunction, expressions);
  }

  @Override
  public boolean isConstant() {

    return true;
  }

  /**
   * @param value the {@code boolean} value the requested {@link ConstantExpression} should {@link #evaluate() evaluate}
   *        to.
   * @return {@link #TRUE} if {@code value} is {@code true}, {@link #FALSE} otherwise.
   */
  public static ConstantExpression valueOf(boolean value) {

    if (value) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  @Override
  public void format(ExpressionFormatter formatter, Bracketing bracketing) {

    formatter.getBuffer().append(Boolean.toString(evaluate()));
  }

}
