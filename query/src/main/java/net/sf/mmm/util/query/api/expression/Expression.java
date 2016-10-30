/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.expression;

import java.util.Arrays;
import java.util.Collection;

import net.sf.mmm.util.lang.api.Conjunction;

/**
 * An {@link Expression} or predicate that checks a value or {@link net.sf.mmm.util.property.api.ReadableProperty
 * property}.
 *
 * @see net.sf.mmm.util.query.base.expression.Expressions
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface Expression {

  /**
   * @see java.util.function.Predicate#test(Object)
   *
   * @return {@code true} if the expression is fulfilled, {@code false} otherwise.
   */
  boolean evaluate();

  /**
   * @return the negation of this {@link Expression}.
   */
  Expression negate();

  /**
   * @param conjunction the {@link Conjunction}.
   * @param terms the {@link Collection} of the {@link Expression}s to combine with this one.
   * @return a new {@link Expression} combining this {@link Expression} with the given {@code Expression}s using the
   *         given {@link Conjunction}.
   */
  Expression combine(Conjunction conjunction, Collection<Expression> terms);

  /**
   * @param conjunction the {@link Conjunction}.
   * @param terms the array of the {@link Expression}s to combine with this one.
   * @return a new {@link Expression} combining this {@link Expression} with the given {@code Expression}s using the
   *         given {@link Conjunction}.
   */
  default Expression combine(Conjunction conjunction, Expression... terms) {

    return combine(conjunction, Arrays.asList(terms));
  }

  /**
   * @param terms the array of the {@link Expression}s to combine with this one.
   * @return a new {@link Expression} combining this {@link Expression} with the given {@code Expression}s using a
   *         {@link Conjunction#OR logical OR}.
   */
  default Expression or(Expression... terms) {

    return combine(Conjunction.OR, terms);
  }

  /**
   * @param terms the array of the {@link Expression}s to combine with this one.
   * @return a new {@link Expression} combining this {@link Expression} with the given {@code Expression}s using a
   *         {@link Conjunction#AND logical AND}.
   */
  default Expression and(Expression... terms) {

    return combine(Conjunction.AND, terms);
  }

  /**
   * @return {@code true} if this is a {@link net.sf.mmm.util.query.base.expression.ConstantExpression constant
   *         expression} that {@link #evaluate() evaluates} to a fixed result, {@code false} otherwise.
   */
  boolean isConstant();

  /**
   * @param formatter the {@link ExpressionFormatter} where to {@link ExpressionFormatter#append(CharSequence) append}
   *        this {@link Expression}.
   * @param bracketing the {@link Bracketing}.
   * @param resolveNegativeConjunctions {@code true} if {@link Conjunction#isNegation() negative} {@link Conjunction}s
   *        should be resolved via negation (e.g. {@code !(a OR b)} instead of {@code a NOR b}), {@code false}
   *        otherwise.
   */
  void format(ExpressionFormatter formatter, Bracketing bracketing);

}
