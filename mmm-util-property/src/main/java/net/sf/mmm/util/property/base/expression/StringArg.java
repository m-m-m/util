/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.expression.StringArgument;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.property.base.expression.SqlOperator.SqlOperatorLike;

/**
 * This is the implementation of {@link StringArgument}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class StringArg extends ComparableArg<String> implements StringArgument {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public StringArg(String value) {
    super(value);
  }

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public StringArg(PropertyPath<String> path) {
    super(path);
  }

  @Override
  public Expression like(String pattern) {

    return exp(SqlOperator.LIKE, pattern);
  }

  @Override
  public Expression like(String pattern, char escape) {

    return exp(new SqlOperatorLike(true, escape), pattern);
  }

}
