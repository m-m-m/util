/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.path;

import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the interface for a typed path.
 *
 * @see net.sf.mmm.util.pojo.path.api.PojoPropertyPath
 * @see net.sf.mmm.util.property.api.ReadableProperty
 *
 * @param <V> the generic type of the property value identified by this path.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface PropertyPath<V> extends AttributeReadName, AttributeReadValue<V> {

  /**
   * @return the name what is the {@link String} representation of this path. In case of a
   *         {@link net.sf.mmm.util.property.api.ReadableProperty single property} the name of the property. Otherwise
   *         an entire path (such as e.g. "entity.property").
   */
  @Override
  String getName();

}
