/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import java.util.Collection;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;

/**
 * This is the implementation of {@link Expression}.
 *
 * @param <L> the generic type of the {@link #getArg1() left hand}.
 * @param <R> the generic type of the {@link #getArg1() right hand}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ExpressionImpl<L, R> implements Expression {

  private final Arg<L> arg1;

  private final Arg<R> arg2;

  private final SqlOperator<? super L, ? super R> operator;

  /**
   * The constructor.
   *
   * @param arg1 - see {@link #getArg1()}.
   * @param operator - see {@link #getOperator()}.
   * @param arg2 - see {@link #getArg2()}.
   */
  public ExpressionImpl(Arg<L> arg1, SqlOperator<? super L, ? super R> operator, Arg<R> arg2) {
    super();
    this.arg1 = arg1;
    this.operator = operator;
    this.arg2 = arg2;
  }

  @Override
  public Expression negate() {

    return new ExpressionImpl<>(this.arg1, this.operator.negate(), this.arg2);
  }

  /**
   * @return the first argument (left hand).
   */
  public Arg<L> getArg1() {

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
  public Arg<R> getArg2() {

    return this.arg2;
  }

  @Override
  public boolean evaluate() {

    return this.operator.evaluate(this.arg1.evaluate(), this.arg2.evaluate());
  }

  @Override
  public Expression combine(Conjunction conjunction, Collection<Expression> expressions) {

    int size = expressions.size();
    Expression[] children = new Expression[size + 1];
    expressions.toArray(children);
    children[size] = this;
    return new ConjectionExpression(conjunction, children);
  }

  @Override
  public boolean isStatic() {

    return (this.arg1.isStatic() && this.arg2.isStatic());
  }

}
