/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.path;

import java.util.Collection;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.query.api.path.CollectionPath;
import net.sf.mmm.util.query.api.path.NumberPath;
import net.sf.mmm.util.query.api.path.PathRoot;
import net.sf.mmm.util.query.base.argument.AbstractCollectionArgument;

/**
 * This is the implementation of {@link CollectionPath}.
 *
 * @param <V> the generic type of the property value identified by this path.
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class CollectionPathImpl<V extends Collection<E>, E> extends PathImpl<V>
    implements CollectionPath<V, E>, AbstractCollectionArgument<V, E> {

  /**
   * The constructor.
   *
   * @param parent - {@link #getParent()}.
   * @param property - see {@link #getProperty()}.
   */
  public CollectionPathImpl(PathImpl<?> parent, ReadableProperty<?> property) {
    super(parent, property);
  }

  /**
   * The constructor.
   *
   * @param root - {@link #getRoot()}.
   * @param property - see {@link #getProperty()}.
   */
  public CollectionPathImpl(PathRoot<?> root, ReadableProperty<?> property) {
    super(root, property);
  }

  @Override
  public NumberPath<Integer> size() {

    return new NumberPathImpl<>(this, SIZE_PROPERTY);
  }

}
