/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.ComparablePath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractComparableArgument;

/**
 * This is the implementation of {@link ComparablePath}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ComparablePathImpl<V extends Comparable<?>> extends PathImpl<V>
    implements ComparablePath<V>, AbstractComparableArgument<V> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public ComparablePathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public ComparablePathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
