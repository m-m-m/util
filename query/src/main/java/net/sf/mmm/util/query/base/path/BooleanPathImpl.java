/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.BooleanPath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractBooleanArgument;

/**
 * This is the implementation of {@link BooleanPath}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class BooleanPathImpl extends PathImpl<Boolean> implements BooleanPath, AbstractBooleanArgument {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public BooleanPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public BooleanPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
