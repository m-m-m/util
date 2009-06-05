/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Array;
import java.util.Collection;

import net.sf.mmm.util.lang.api.StringTokenizer;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to an array.<br>
 * Java has a string type-system and arrays of primitive types (int[], etc.) can
 * therefore NOT be casted to Object[]. However Object is NOT specific enough so
 * we need to supply multiple {@link net.sf.mmm.util.value.api.ValueConverter}
 * 
 * @param <ARRAY> is the generic type of the array as {@link #getTargetType()
 *        target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConverterToArray<ARRAY> extends
    AbstractRecursiveValueConverter<Object, ARRAY> {

  /**
   * The constructor.
   */
  public AbstractConverterToArray() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
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
  @SuppressWarnings("unchecked")
  public ARRAY convert(Object value, Object valueSource, GenericType<? extends ARRAY> targetType) {

    if (value == null) {
      return null;
    }
    ComposedValueConverter parentConverter = getComposedValueConverter();
    Class<?> valueClass = value.getClass();
    GenericType<?> arrayComponentType = targetType.getComponentType();
    Object result = null;
    if (valueClass.isArray()) {
      int size = Array.getLength(value);
      result = Array.newInstance(arrayComponentType.getAssignmentClass(), size);
      for (int i = 0; i < size; i++) {
        Object element = Array.get(value, i);
        Object resultElement = parentConverter.convert(element, valueSource, arrayComponentType);
        Array.set(result, i, resultElement);
      }
    } else if (value instanceof Collection) {
      Collection<?> collection = (Collection) value;
      int size = collection.size();
      result = Array.newInstance(arrayComponentType.getAssignmentClass(), size);
      int i = 0;
      for (Object element : collection) {
        Object resultElement = parentConverter.convert(element, valueSource, arrayComponentType);
        Array.set(result, i, resultElement);
        i++;
      }
    } else if (value instanceof CharSequence) {
      char[] valueChars = value.toString().toCharArray();
      int size = 0;
      if (valueChars.length > 0) {
        size++;
        for (int i = 0; i < valueChars.length; i++) {
          if (valueChars[i] == ',') {
            size++;
          }
        }
      }
      result = Array.newInstance(arrayComponentType.getAssignmentClass(), size);
      StringTokenizer tokenizer = new StringTokenizer(valueChars, ',');
      int i = 0;
      for (String element : tokenizer) {
        Object resultElement = parentConverter.convert(element, valueSource, arrayComponentType);
        Array.set(result, i, resultElement);
        i++;
      }
    }
    if (result == null) {
      return null;
    }
    return getTargetType().cast(result);
  }
}
