/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.expression;

import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.path.Path;
import net.sf.mmm.util.query.base.expression.SqlOperator;

/**
 * This is a callback interface used to {@link Expression#format(ExpressionFormatter, Bracketing) format}
 * {@link Expression}s.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface ExpressionFormatter {

  /**
   * @return the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the {@link Expression}
   *         to {@link Expression#format(ExpressionFormatter, Bracketing) format}.
   */
  StringBuilder getBuffer();

  /**
   * @param argument the {@link Argument} to append.
   */
  default void append(Argument<?> argument) {

    Path<?> path = argument.getValuePath();
    if (path != null) {
      getBuffer().append(path.getName());
    } else {
      getBuffer().append("" + argument.getValue());
    }
  }

  /**
   * @param operator the {@link SqlOperator} to append.
   * @param argument the {@link Argument} to append.
   */
  default void append(SqlOperator<?, ?> operator, Argument<?> argument) {

    getBuffer().append(operator.getSql());
    append(argument);
  }

  /**
   * @param string the {@link CharSequence} to {@link StringBuilder#append(CharSequence) append} to the
   *        {@link #getBuffer() buffer}.
   */
  default void append(CharSequence string) {

    getBuffer().append(string);
  }

  /**
   * @param object the {@link Object} to {@link StringBuilder#append(CharSequence) append} to the {@link #getBuffer()
   *        buffer}.
   */
  default void append(Object object) {

    getBuffer().append(object);
  }

  /**
   * @param conjunction the {@link Conjunction} to append.
   * @return the preferred {@link String} representation of the {@link Conjunction}.
   */
  default String format(Conjunction conjunction) {

    return conjunction.name();
  }

  /**
   * @return {@code true} if {@link Conjunction#isNegation() negative} {@link Conjunction}s should be resolved via
   *         negation (e.g. {@code !(a OR b)} instead of {@code a NOR b}), {@code false} otherwise.
   */
  boolean isResolveNegativeConjunctions();

}
