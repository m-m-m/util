/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Expression;

/**
 * Implementation of {@link Expression} for logical {@link Conjunction} of a list of {@link Expression}s.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConjunctionExpression implements Expression {

  private final Conjunction conjunction;

  private final List<Expression> terms;

  /**
   * The constructor.
   *
   * @param conjunction - see {@link #getConjunction()}.
   * @param terms the terms to combine.
   */
  private ConjunctionExpression(Conjunction conjunction, List<Expression> terms) {
    super();
    this.conjunction = conjunction;
    this.terms = Collections.unmodifiableList(terms);
  }

  /**
   * @return the {@link Conjunction}.
   */
  public Conjunction getConjunction() {

    return this.conjunction;
  }

  /**
   * @return an {@link Collections#unmodifiableList(List) unmodifiable} {@link List} of the {@link Expression}-Terms to
   *         {@link #combine(Conjunction, Collection) combine} with the {@link #getConjunction() conjunction}.
   */
  public List<Expression> getTerms() {

    return this.terms;
  }

  @Override
  public Expression negate() {

    return new ConjunctionExpression(this.conjunction.negate(), this.terms);
  }

  @Override
  public boolean evaluate() {

    boolean[] arguments = new boolean[this.terms.size()];
    int i = 0;
    for (Expression term : this.terms) {
      arguments[i++] = term.evaluate();
    }
    return this.conjunction.eval(arguments);
  }

  @Override
  public Expression combine(Conjunction conj, Collection<Expression> expressions) {

    int capacity = expressions.size();
    if (this.conjunction == conj) {
      capacity = capacity + this.terms.size();
    } else {
      capacity++;
    }
    List<Expression> children = new ArrayList<>(capacity);
    if (this.conjunction == conj) {
      children.addAll(this.terms);
    } else {
      children.add(this);
    }
    return valueOf(children, conj, expressions);
  }

  @Override
  public boolean isConstant() {

    return false;
  }

  /**
   * @param conjunction the {@link #getConjunction() conjunction}.
   * @param expressions the {@link Expression} {@link #getTerms() terms}.
   * @return the new {@link Expression}. May be reduced and therefore does not have to be an instance of
   *         {@link ConjunctionExpression}.
   */
  public static Expression valueOf(Conjunction conjunction, Expression... expressions) {

    return valueOf(conjunction, Arrays.asList(expressions));
  }

  /**
   * @param conjunction the {@link #getConjunction() conjunction}.
   * @param expressions the {@link Expression} {@link #getTerms() terms}.
   * @return the new {@link Expression}. May be reduced and therefore does not have to be an instance of
   *         {@link ConjunctionExpression}.
   */
  public static Expression valueOf(Conjunction conjunction, Collection<Expression> expressions) {

    return valueOf(new ArrayList<>(expressions.size()), conjunction, expressions);
  }

  /**
   * @param expression the start expression to {@link #combine(Conjunction, Collection) combine} with the given
   *        {@link Expression}-{@link Collection}.
   * @param conjunction the {@link #getConjunction() conjunction}.
   * @param expressions the {@link Expression} {@link #getTerms() terms}.
   * @return the new {@link Expression}. May be reduced and therefore does not have to be an instance of
   *         {@link ConjunctionExpression}.
   */
  public static Expression valueOf(Expression expression, Conjunction conjunction,
      Collection<Expression> expressions) {

    if (expression.isConstant()) {
      Boolean single = conjunction.evalSingle(expression.evaluate());
      if (single != null) {
        return ConstantExpression.valueOf(single.booleanValue());
      }
    }
    ArrayList<Expression> children = new ArrayList<>(expressions.size() + 1);
    children.add(expression);
    return valueOf(children, conjunction, expressions);
  }

  private static Expression valueOf(List<Expression> children, Conjunction conjunction,
      Collection<Expression> expressions) {

    for (Expression expression : expressions) {
      if (expression.isConstant()) {
        boolean result = expression.evaluate();
        Boolean single = conjunction.evalSingle(result);
        if (single != null) {
          return ConstantExpression.valueOf(single.booleanValue());
        }
      } else {
        children.add(expression);
      }
    }
    if (children.isEmpty()) {
      return ConstantExpression.valueOf(conjunction.evalEmpty());
    }
    return new ConjunctionExpression(conjunction, children);
  }

}
