/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.api.path.StringPath;
import net.sf.mmm.util.query.base.argument.AbstractStringArgument;

/**
 * This is the implementation of {@link StringPath}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class StringPathImpl extends PathImpl<String> implements StringPath, AbstractStringArgument {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public StringPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public StringPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
