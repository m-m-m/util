/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.factory;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the interface for the factory of a specific {@link ReadableProperty property} type. You can directly
 * instantiate implementations such as {@link net.sf.mmm.util.property.api.StringProperty}. However for generic support
 * such as {@link net.sf.mmm.util.bean.api.BeanAccess#createProperty(String, Class)} according types have to be
 * registered via an implementation of this interface.
 *
 * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
 * @param <PROPERTY> the generic type of the {@link WritableProperty property}.
 *
 * @see PropertyFactoryManager
 *
 * @author hohwille
 * @since 8.0.0
 */
@ComponentSpecification(plugin = true)
public interface PropertyFactory<V, PROPERTY extends WritableProperty<V>> {

  /**
   * @return the {@link Class} of the {@link WritableProperty#getValue() property value}. May be <code>null</code> for
   *         {@link net.sf.mmm.util.property.api.GenericProperty}.
   */
  Class<? extends V> getValueClass();

  /**
   * @return the {@link Class} reflecting the {@link ReadableProperty} interface.
   */
  Class<? extends ReadableProperty<V>> getReadableInterface();

  /**
   * @return the {@link Class} reflecting the {@link WritableProperty} interface.
   */
  Class<? extends WritableProperty<V>> getWritableInterface();

  /**
   * @return the {@link Class} reflecting the {@link WritableProperty} implementation.
   */
  Class<PROPERTY> getImplementationClass();

  /**
   * Creates a new instance of the property.
   *
   * @param name the {@link ReadableProperty#getName() property name}.
   * @param valueType is the {@link GenericType} of the value. Only needed for generic properties such as
   *        {@link net.sf.mmm.util.property.api.GenericProperty} or {@link net.sf.mmm.util.property.api.ListProperty}.
   *        Can be {@code null} if the generic value type is already bound and should be ignored then.
   * @param bean the {@link ReadableProperty#getBean() property bean}.
   * @param validator the {@link AbstractValidator validator} used for {@link WritableProperty#validate() validation}.
   *        May be {@code null}.
   *
   * @return the new instance of the property.
   */
  PROPERTY create(String name, GenericType<V> valueType, Bean bean, AbstractValidator<? super V> validator);

}
