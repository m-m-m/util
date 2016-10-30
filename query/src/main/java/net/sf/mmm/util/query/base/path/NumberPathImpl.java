/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.NumberPath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractNumberArgument;

/**
 * This is the implementation of {@link NumberPath}.
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class NumberPathImpl<V extends Number & Comparable<?>> extends ComparablePathImpl<V>
    implements NumberPath<V>, AbstractNumberArgument<V> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public NumberPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public NumberPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
