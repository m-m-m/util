/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.path;

import java.util.function.Function;

import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
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
 * @see PathFactory
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface Path<V> extends PathFactory, PropertyPath<V>, Argument<V> {

  /**
   * @return this entire {@link Path} as {@link String} (e.g. "alias.property", "size(alias.children)", or
   *         "$variable.link.property").
   */
  @Override
  default String getName() {

    StringBuilder buffer = new StringBuilder();
    format(x -> x.getName(), buffer);
    return buffer.toString();
  }

  /**
   * @see #getName()
   *
   * @param segmentFormatter a {@link Function} used to {@link Function#apply(Object) format} a segment of this
   *        {@link Path} to its {@link String} representation. This formatter will receive instances of {@link PathRoot}
   *        or {@link net.sf.mmm.util.property.api.ReadableProperty}.
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(CharSequence) append} the resulting
   *        {@link String} representation of this {@link Path}.
   */
  void format(Function<AttributeReadName, String> segmentFormatter, StringBuilder buffer);

  /**
   * @return the {@link PathRoot root} of this {@link Path}. Will e.g. be an
   *         {@link net.sf.mmm.util.query.base.path.Alias} or a {@link net.sf.mmm.util.query.api.variable.Variable} and
   *         NOT an instance of {@link Path}.
   */
  PathRoot<?> getRoot();

}
