/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Array;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ComposedValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an {@link Object} to an array. <br>
 * Java has a string type-system and arrays of primitive types (int[], etc.) can therefore NOT be casted to
 * Object[]. However Object is NOT specific enough so we need to supply multiple
 * {@link net.sf.mmm.util.value.api.ValueConverter}
 *
 * @param <ARRAY> is the generic type of the array as {@link #getTargetType() target-type}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractConverterToArray<ARRAY> extends AbstractValueConverterToContainer<ARRAY> {

  /**
   * The constructor.
   */
  public AbstractConverterToArray() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    assert getTargetType().isArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void convertContainerEntry(Object element, int index, ARRAY container, Object valueSource,
      GenericType<? extends ARRAY> targetType, Object value) {

    ComposedValueConverter parentConverter = getComposedValueConverter();
    GenericType<?> componentType = targetType.getComponentType();
    Object resultElement = parentConverter.convert(element, valueSource, componentType);
    if ((resultElement == null) && (element != null)) {
      Exception cause = new NlsParseException(element, componentType, valueSource);
      throw new NlsParseException(cause, value, targetType, valueSource);
    }
    Array.set(container, index, resultElement);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  protected <T extends ARRAY> T createContainer(GenericType<T> targetType, int length) {

    return (T) Array.newInstance(targetType.getComponentType().getAssignmentClass(), length);
  }

}
