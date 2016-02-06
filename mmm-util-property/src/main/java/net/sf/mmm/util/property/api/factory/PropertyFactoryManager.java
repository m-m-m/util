/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.factory;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the interface for the manager where all {@link PropertyFactory} variants are registered.
 *
 * @author hohwille
 * @since 8.0.0
 */
@ComponentSpecification
public interface PropertyFactoryManager {

  /**
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param <PROPERTY> the generic type of the {@link WritableProperty property}.
   * @param propertyType the {@link Class} reflecting the property to create. May be the
   *        {@link PropertyFactory#getReadableInterface() readable interface},
   *        {@link PropertyFactory#getWritableInterface() writable interface}, or the
   *        {@link PropertyFactory#getImplementationClass() implementation}.
   * @return the according {@link PropertyFactory} or {@code null} if no such factory is registered.
   */
  <V, PROPERTY extends ReadableProperty<V>> PropertyFactory<V, ? extends PROPERTY> getFactory(
      Class<PROPERTY> propertyType);

  /**
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param <PROPERTY> the generic type of the {@link WritableProperty property} to create.
   * @param propertyType the {@link Class} reflecting the property to create. May be the
   *        {@link PropertyFactory#getReadableInterface() readable interface},
   *        {@link PropertyFactory#getWritableInterface() writable interface}, or the
   *        {@link PropertyFactory#getImplementationClass() implementation}.
   * @param name the {@link ReadableProperty#getName() property name}.
   * @param valueType is the {@link GenericType} of the value. Only needed for generic properties such as
   *        {@link net.sf.mmm.util.property.api.GenericProperty} or {@link net.sf.mmm.util.property.api.ListProperty}.
   *        Can be {@code null} if the generic value type is already bound and should be ignored then.
   * @param bean the {@link ReadableProperty#getBean() property bean}.
   * @param validator the {@link AbstractValidator validator} used for {@link WritableProperty#validate() validation}.
   *        May be {@code null}.
   * @return the new instance of the property.
   * @throws ObjectNotFoundException if no {@link PropertyFactory} was {@link #getFactory(Class) found} for
   *         {@code propertyType}.
   */
  <V, PROPERTY extends ReadableProperty<V>> PROPERTY create(Class<PROPERTY> propertyType, String name,
      GenericType<V> valueType, Bean bean, AbstractValidator<? super V> validator) throws ObjectNotFoundException;

}
