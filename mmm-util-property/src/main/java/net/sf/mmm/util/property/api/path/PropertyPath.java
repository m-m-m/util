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
 * @since 8.0.0
 */
public interface PropertyPath<V> extends AttributeReadName, AttributeReadValue<V> {

}
