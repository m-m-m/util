/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import java.util.Collection;
import java.util.List;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.ListPath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractListArgument;

/**
 * This is the implementation of {@link ListPath}.
 *
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ListPathImpl<E> extends CollectionPathImpl<List<E>, E>
    implements ListPath<E>, AbstractListArgument<E> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public ListPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public ListPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

}
