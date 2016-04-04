/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base.expression;

import net.sf.mmm.util.property.api.expression.ComparableArgument;
import net.sf.mmm.util.property.api.expression.Expression;
import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This is the implementation of {@link ComparableArgument}.
 *
 * @param <V> the generic type of the value the check by the {@link Expression} to build.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ComparableArg<V extends Comparable<?>> extends Arg<V>
    implements ComparableArgument<V> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ComparableArg(V value) {
    super(value);
  }

  /**
   * The constructor.
   *
   * @param path the {@link #getPath() path}.
   */
  public ComparableArg(PropertyPath<V> path) {
    super(path);
  }

}
