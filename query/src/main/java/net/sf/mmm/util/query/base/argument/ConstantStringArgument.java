/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.StringArgument;

/**
 * This is the {@link #isConstant() constant} implementation of {@link StringArgument}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstantStringArgument extends ConstantComparableArgument<String> implements AbstractStringArgument {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantStringArgument(String value) {
    super(value);
  }

}
