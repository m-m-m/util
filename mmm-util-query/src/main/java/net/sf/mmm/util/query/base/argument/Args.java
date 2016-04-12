/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import net.sf.mmm.util.query.api.argument.Argument;
import net.sf.mmm.util.query.api.argument.BooleanArgument;
import net.sf.mmm.util.query.api.argument.ComparableArgument;
import net.sf.mmm.util.query.api.argument.NumberArgument;
import net.sf.mmm.util.query.api.argument.StringArgument;

/**
 * Static factory to create {@link Argument}s.
 *
 * @author hohwille
 * @since 8.0.0
 */
public final class Args {

  private Args() {
  }

  public static <V> Argument<V> of(V value) {

    return new ConstantArgument<>(value);
  }

  public static <V extends Comparable<?>> ComparableArgument<V> of(V value) {

    return new ConstantComparableArgument<>(value);
  }

  public static BooleanArgument of(Boolean value) {

    return new ConstantBooleanArgument(value);
  }

  public static StringArgument of(String value) {

    return new ConstantStringArgument(value);
  }

  public static <N extends Number & Comparable<?>> NumberArgument<N> of(N value) {

    return new ConstantNumberArgument<>(value);
  }

}
