/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import java.util.Collection;

import net.sf.mmm.util.query.api.argument.CollectionArgument;

/**
 * This is the interface for a {@link Path} that is a {@link CollectionArgument}.
 *
 * @param <V> the generic type of the property value identified by this path.
 * @param <E> the generic type of the {@link Collection}-{@link Collection#contains(Object) elements}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface CollectionPath<V extends Collection<E>, E> extends Path<V>, CollectionArgument<V, E> {

  /**
   * @return a {@link NumberPath} for {@code this.}{@link Collection#size() size()}.
   */
  NumberPath<Integer> size();

}
