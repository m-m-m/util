/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.argument;

import java.util.Map;

import net.sf.mmm.util.query.api.argument.MapArgument;

/**
 * This is the {@link #isConstant() constant} implementation of {@link MapArgument}.
 *
 * @param <K> the generic type of the {@link Map}-{@link Map#keySet() keys}.
 * @param <V> the generic type of the {@link Map}-{@link Map#values() values}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class ConstantMapArgument<K, V> extends ConstantArgument<Map<K, V>> implements AbstractMapArgument<K, V> {

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public ConstantMapArgument(Map<K, V> value) {
    super(value);
  }

}
