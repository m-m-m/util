/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import net.sf.mmm.util.pojo.path.api.TypedPath;
import net.sf.mmm.util.property.api.path.PropertyPath;
import net.sf.mmm.util.query.api.argument.Argument;

/**
 * This is the interface for a typed path {@link #to(net.sf.mmm.util.property.api.ReadableProperty) build} by the
 * {@link net.sf.mmm.util.property.api.ReadableProperty properties} to traverse.
 *
 * @see net.sf.mmm.util.query.base.path.Alias
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface Path<V> extends PathFactory, PropertyPath<V>, TypedPath<V>, Argument<V> {

  /**
   * @return the {@link PathRoot root} of this {@link Path}. Will e.g. be an
   *         {@link net.sf.mmm.util.query.base.path.Alias} or a {@link net.sf.mmm.util.query.api.variable.Variable} and
   *         NOT an instance of {@link Path}.
   */
  PathRoot<?> getRoot();

}
