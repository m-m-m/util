/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import java.util.Collection;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;

/**
 * Implementation of {@link Expression} for logical {@link net.sf.mmm.util.lang.api.Conjunction#OR OR}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConjectionExpression implements Expression {

  private final Conjunction conjunction;

  private final Expression[] terms;

  /**
   * The constructor.
   *
   * @param conjunction - see {@link #getConjunction()}.
   * @param terms the terms to combine with OR.
   */
  public ConjectionExpression(Conjunction conjunction, Expression... terms) {
    super();
    this.conjunction = conjunction;
    this.terms = terms;
  }

  /**
   * @return the {@link Conjunction}.
   */
  public Conjunction getConjunction() {

    return this.conjunction;
  }

  /**
   * @see java.util.Collection#size()
   *
   * @return the number of {@link #getTerm(int) terms}.
   */
  public int getTermCount() {

    return this.terms.length;
  }

  /**
   * @see java.util.List#get(int)
   *
   * @param index the {@link java.util.List#get(int) index} of the requested term.
   * @return the {@link Expression}-term at the given {@code index}.
   */
  public Expression getTerm(int index) {

    return this.terms[index];
  }

  @Override
  public Expression negate() {

    return new ConjectionExpression(this.conjunction.negate(), this.terms);
  }

  @Override
  public boolean evaluate() {

    boolean[] arguments = new boolean[this.terms.length];
    int i = 0;
    for (Expression term : this.terms) {
      arguments[i++] = term.evaluate();
    }
    return this.conjunction.eval(arguments);
  }

  @Override
  public Expression combine(Conjunction conj, Collection<Expression> expressions) {

    Expression[] children;
    int size = expressions.size();
    if (this.conjunction == conj) {
      children = new Expression[size + this.terms.length];
      expressions.toArray(children);
      System.arraycopy(this.terms, 0, children, size, this.terms.length);
    } else {
      children = new Expression[size + 1];
      expressions.toArray(children);
      children[size] = this;
    }
    return new ConjectionExpression(conj, children);
  }

  @Override
  public boolean isStatic() {

    for (Expression expression : this.terms) {
      if (!expression.isStatic()) {
        return false;
      }
    }
    return true;
  }

}
