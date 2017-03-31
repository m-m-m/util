/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import java.util.Set;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.api.path.SetPath;
import net.sf.mmm.util.query.base.argument.AbstractSetArgument;

/**
 * This is the implementation of {@link SetPath}.
 *
 * @param <E> the generic type of the {@link Set}-{@link Set#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class SetPathImpl<E> extends CollectionPathImpl<Set<E>, E> implements SetPath<E>, AbstractSetArgument<E> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public SetPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public SetPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
