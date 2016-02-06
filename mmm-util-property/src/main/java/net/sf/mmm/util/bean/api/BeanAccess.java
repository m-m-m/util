/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import javax.inject.Named;

import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.ValidationFailureComposer;

/**
 * This is the interface for all generic operations on a {@link Bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface BeanAccess {

  /**
   * @return an {@link Iterable} with all the properties of this {@link Bean}.
   */
  Iterable<WritableProperty<?>> getProperties();

  /**
   * @param name the {@link WritableProperty#getName() name} of the requested property.
   * @return the requested {@link WritableProperty} or <code>null</code> if no such property exists.
   */
  WritableProperty<?> getProperty(String name);

  /**
   * @return the {@link Class} reflecting the owning {@link Bean}.
   */
  Class<? extends Bean> getBeanClass();

  /**
   * @return the {@link Named#value() value} of the {@link Bean}s {@link Named} annotation or the
   *         {@link Class#getSimpleName() simple name} of the {@link #getBeanClass() bean class} if no such annotation
   *         is present.
   */
  String getName();

  /**
   * @param name the {@link WritableProperty#getName() name} of the requested property.
   * @return the requested {@link WritableProperty}.
   * @throws PojoPropertyNotFoundException if the requested property does not exist.
   */
  default WritableProperty<?> getRequiredProperty(String name) throws PojoPropertyNotFoundException {

    WritableProperty<?> property = getProperty(name);
    if (property == null) {
      throw new PojoPropertyNotFoundException(getBeanClass(), name);
    }
    return property;
  }

  /**
   * @see ValueValidator#validate(Object)
   *
   * @return the {@link ValidationFailure} or <code>null</code> if this {@link Bean} is valid according to this
   *         {@link ValueValidator}.
   */
  default ValidationFailure validate() {

    ValidationFailureComposer composer = new ValidationFailureComposer();
    for (WritableProperty<?> property : getProperties()) {
      ValidationFailure failure = property.validate();
      composer.add(failure);
    }
    return composer.get(this);
  }

  /**
   * {@link #getProperty(String) Gets} or {@link #createProperty(String, GenericType) creates} the specified property.
   * If the property already exists also the {@link WritableProperty#getType() type} has to match the given {@code type}
   * or an exception will be thrown.
   *
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param type the {@link WritableProperty#getType() property type}.
   * @return the requested property.
   */
  @SuppressWarnings("unchecked")
  default <V> WritableProperty<V> getOrCreateProperty(String name, GenericType<V> type) {

    WritableProperty<?> property = getProperty(name);
    if (property == null) {
      property = createProperty(name, type);
    } else {
      if (!property.getType().equals(type)) {
        throw new ObjectMismatchException(type, property.getType(), name + ".type");
      }
    }
    return (WritableProperty<V>) property;
  }

  /**
   * @param name the {@link WritableProperty#getName() name} of the property.
   * @return the {@link WritableProperty#getValue() value} of the specified property.
   */
  default Object getPropertyValue(String name) {

    WritableProperty<?> property = getProperty(name);
    if (property == null) {
      return null;
    }
    return property.getValue();
  }

  /**
   * @param bean the {@link Bean} instance.
   * @param name the {@link WritableProperty#getName() name} of the property.
   * @param value new {@link WritableProperty#getValue() value} of the specified property.
   */
  default void setPropertyValue(String name, Object value) {

    setPropertyValue(name, value, null);
  }

  /**
   * This method sets the {@link WritableProperty property} with the given {@link WritableProperty#getName() name} to
   * the specified {@code value}.
   *
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param value new {@link WritableProperty#getValue() value} of the specified property.
   * @param type the {@link WritableProperty#getType() property type}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  default <V> void setPropertyValue(String name, V value, GenericType<V> type) {

    WritableProperty property = getProperty(name);
    if (property == null) {
      GenericType<V> genericType = type;
      if (genericType == null) {
        if (value == null) {
          return;
        }
        genericType = (GenericType<V>) ReflectionUtilImpl.getInstance().createGenericType(value.getClass());
      }
      property = createProperty(name, genericType);
    }
    property.setValue(value);
  }

  /**
   * Creates and adds the specified {@link WritableProperty} on the fly. Creating and adding new properties is only
   * possible for {@link #isDynamic() dynamic} beans.
   *
   * @param <V> the generic type of the {@link WritableProperty#getValue() property value}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param type the {@link WritableProperty#getType() property type}.
   * @return the newly created property.
   */
  <V> WritableProperty<V> createProperty(String name, GenericType<V> type);

  /**
   * @see BeanFactory#getReadOnlyBean(Bean)
   *
   * @return <code>true</code> if this {@link Bean} is read-only (immutable), <code>false</code> otherwise.
   */
  boolean isReadOnly();

  /**
   * @return <code>true</code> if this is a dynamic bean that is not strictly typed and can create properties on the
   *         fly, <code>false</code> otherwise.
   */
  boolean isDynamic();

  /**
   * @return <code>true</code> if this {@link BeanAccess} belongs to a {@link BeanFactory#createPrototype(Class)
   *         prototype} , <code>false</code> otherwise (if it belongs to an {@link BeanFactory#create(Bean) instance}).
   */
  boolean isPrototype();

}
