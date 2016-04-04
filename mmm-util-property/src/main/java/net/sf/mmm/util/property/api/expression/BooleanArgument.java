/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.expression;

/**
 * Extends {@link Argument} to build an {@link Expression} for {@link Boolean} value(s).
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface BooleanArgument extends ComparableArgument<Boolean> {

  /**
   * @return an {@link Expression} for {@code this == true}.
   */
  Expression isTrue();

  /**
   * @return an {@link Expression} for {@code this == false}.
   */
  Expression isFalse();

}
