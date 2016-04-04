/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.property.api.expression.Argument;
import net.sf.mmm.util.property.api.expression.BooleanArgument;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.expression.NumberArgument;
import net.sf.mmm.util.property.api.expression.StringArgument;
import net.sf.mmm.util.property.api.path.PropertyPath;

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

    return new ConjectionExpression(conjunction, expressions);
  }

  public static BooleanArgument of(Boolean value) {

    return new BooleanArg(value);
  }

  public static <V> Argument<V> of(PropertyPath<V> path) {

    return new Arg<>(path);
  }

  public static BooleanArgument ofBoolean(PropertyPath<Boolean> path) {

    return new BooleanArg(path);
  }

  public static StringArgument of(String value) {

    return new StringArg(value);
  }

  public static StringArgument ofString(PropertyPath<String> path) {

    return new StringArg(path);
  }

  public static <N extends Number & Comparable<?>> NumberArgument<N> of(N value) {

    return new NumberArg<>(value);
  }

  public static <N extends Number & Comparable<?>> NumberArgument<N> ofNumber(PropertyPath<N> path) {

    return new NumberArg<>(path);
  }

}
