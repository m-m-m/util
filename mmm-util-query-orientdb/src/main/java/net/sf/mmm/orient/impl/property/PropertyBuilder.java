/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.orient.impl.property;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OType;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for the {@link AbstractPropertyBuilder property builder} assembled automatically from various
 * {@link SinglePropertyBuilder}-instances.
 *
 * @author hohwille
 * @since 1.0.0
 */
@ComponentSpecification
public interface PropertyBuilder extends AbstractPropertyBuilder {

  /**
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param property the {@link WritableProperty property} to create in the given {@link OClass}, that is currently
   *        missing.
   * @param oClass the {@link OClass} of the OrientDB schema.
   * @return the created {@link OProperty}. May be {@code null} if the {@link WritableProperty#getType() property type}
   *         is not supported.
   */
  <V> OProperty build(WritableProperty<V> property, OClass oClass);

  /**
   * @param type the {@link OType}.
   * @return the {@link SinglePropertyBuilder} {@link SinglePropertyBuilder#getType() responsible} for the given
   *         {@link OType}.
   */
  SinglePropertyBuilder<?> getBuilder(OType type);

  /**
   * @param <V> the generic type of the given {@link Class}.
   * @param type the raw {@link Class} of the {@link net.sf.mmm.util.property.api.WritableProperty#getType() bean
   *        property value type}.
   * @return the {@link SinglePropertyBuilder} {@link SinglePropertyBuilder#getType() responsible} for the given
   *         {@link Class}.
   */
  <V> SinglePropertyBuilder<? super V> getBuilder(Class<V> type);

}
