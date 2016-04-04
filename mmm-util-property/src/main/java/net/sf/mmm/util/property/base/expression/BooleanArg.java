/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.property.api.expression.BooleanArgument;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the implementation of {@link BooleanArgument}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BooleanArg extends ComparableArg<Boolean> implements BooleanArgument {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public BooleanArg(Boolean value) {
    super(value);
  }

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public BooleanArg(PropertyPath<Boolean> path) {
    super(path);
  }

  @Override
  public Expression isTrue() {

    return exp(SqlOperator.EQUAL, Boolean.TRUE);
  }

  @Override
  public Expression isFalse() {

    return exp(SqlOperator.EQUAL, Boolean.FALSE);
  }

}
