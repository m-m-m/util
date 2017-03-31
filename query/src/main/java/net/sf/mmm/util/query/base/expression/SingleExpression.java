/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.expression;

import java.util.Collection;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.expression.Bracketing;
import net.sf.mmm.util.query.api.expression.Expression;
import net.sf.mmm.util.query.api.expression.ExpressionFormatter;
import net.sf.mmm.util.query.base.argument.ConstantArgument;

/**
 * This is the implementation of {@link Expression} for a single {@link SqlOperator operation} on given arguments.
 *
 * @param <L> the generic type of the {@link #getArg1() left hand}.
 * @param <R> the generic type of the {@link #getArg1() right hand}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class SingleExpression<L, R> implements Expression {

  private final Argument<L> arg1;

  private final Argument<R> arg2;

  private final SqlOperator<? super L, ? super R> operator;

  /**
   * The constructor.
   *
   * @param arg1 - see {@link #getArg1()}.
   * @param operator - see {@link #getOperator()}.
   * @param arg2 - see {@link #getArg2()}.
   */
  private SingleExpression(Argument<L> arg1, SqlOperator<? super L, ? super R> operator, Argument<R> arg2) {
    super();
    this.arg1 = arg1;
    this.operator = operator;
    this.arg2 = arg2;
  }

  @Override
  public Expression negate() {

    return new SingleExpression<>(this.arg1, this.operator.negate(), this.arg2);
  }

  /**
   * @return the first argument (left hand).
   */
  public Argument<L> getArg1() {

    return this.arg1;
  }

  /**
   * @return the {@link SqlOperator}.
   */
  public SqlOperator<? super L, ? super R> getOperator() {

    return this.operator;
  }

  /**
   * @return the second argument (right hand).
   */
  public Argument<R> getArg2() {

    return this.arg2;
  }

  @Override
  public boolean evaluate() {

    return this.operator.evaluate(this.arg1.evaluate(), this.arg2.evaluate());
  }

  @Override
  public Expression combine(Conjunction conjunction, Collection<Expression> expressions) {

    return ConjunctionExpression.valueOf(this, conjunction, expressions);
  }

  @Override
  public boolean isConstant() {

    return false;
  }

  /**
   * @param <L> the generic type of the {@link #getArg1() left hand}.
   * @param <R> the generic type of the {@link #getArg1() right hand}.
   * @param arg1 - see {@link #getArg1()}.
   * @param operator - see {@link #getOperator()}.
   * @param arg2 - see {@link #getArg2()}.
   * @return a new {@link Expression} for a single {@link SqlOperator operation} on given arguments. Will be reduced to
   *         a {@link ConstantExpression} if both {@link ConstantArgument}s are {@link ConstantArgument#isConstant()
   *         constant} and therefore does not have to be an instance of {@link SingleExpression}.
   */
  public static <L, R> Expression valueOf(Argument<L> arg1, SqlOperator<? super L, ? super R> operator,
      Argument<R> arg2) {

    if (arg1.isConstant() && arg2.isConstant()) {
      return ConstantExpression.valueOf(operator.evaluate(arg1.evaluate(), arg2.evaluate()));
    }
    return new SingleExpression<>(arg1, operator, arg2);
  }

  @Override
  public void format(ExpressionFormatter formatter, Bracketing bracketing) {

    formatter.append(this.arg1);
    formatter.append(this.operator, this.arg2);
  }

}
