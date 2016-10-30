/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.argument;

import net.sf.mmm.util.query.api.expression.Expression;

/**
 * Extends {@link Argument} to build an {@link Expression} of {@link String} value(s).
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface StringArgument extends Argument<String> {

  /**
   * @param pattern the like value pattern to match. Use percent (%) to match any substring (including empty string) and
   *        underscore (_) to match any single character.
   * @param escape the escape character.
   * @return an {@link Expression} for {@code this == true}.
   */
  Expression like(String pattern);

  /**
   * @param pattern the like value pattern to match. Use percent (%) to match any substring (including empty string) and
   *        underscore (_) to match any single character.
   * @param escape the escape character to escape % and _ (e.g. the backslash '\\').
   * @return an {@link Expression} for {@code this == true}.
   */
  Expression like(String pattern, char escape);

}
