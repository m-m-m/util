/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import java.util.Map;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.MapPath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractMapArgument;

/**
 * This is the implementation of {@link MapPath}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class MapPathImpl<K, V> extends PathImpl<Map<K, V>> implements MapPath<K, V>, AbstractMapArgument<K, V> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public MapPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public MapPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
