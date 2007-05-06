/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.value.api.ValueNotEditableException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.WrongValueTypeException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValue} interface using a templated
 * {@link #getPlainValue() value}.
 * 
 * @param <V>
 *        is the templated type of the actual {@link #getPlainValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractTemplatedGenericValue<V> extends AbstractGenericValue {

  /**
   * The constructor.
   */
  public AbstractTemplatedGenericValue() {

    super();
  }

  /**
   * This method gets the plain object that holds the actual value.
   * 
   * @return the value object or <code>null</code> if the value
   *         {@link #isEmpty() is empty}.
   */
  protected abstract V getPlainValue();

  /**
   * This method sets the plain value object.
   * 
   * @param newValue
   *        is the new value to set.
   */
  protected abstract void setPlainValue(V newValue);

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {

    return (getPlainValue() == null);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getValue(Class<T> type) {

    V value = getPlainValue();
    if (value == null) {
      throw new ValueNotSetException(this);
    }
    return convertValue(type, value);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getValue(Class<T> type, T defaultValue) {

    T result;
    V value = getPlainValue();
    if (value == null) {
      result = defaultValue;
      if (isAddDefaults() && (defaultValue != null)) {
        setPlainValue(convertValue(defaultValue));
      }
    } else {
      result = convertValue(type, value);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void setObject(Object newValue) throws ValueNotEditableException, WrongValueTypeException {

    if (isEditable()) {
      setPlainValue(convertValue(newValue));
    } else {
      throw new ValueNotEditableException(this);
    }
  }

  /**
   * This method converts the given <code>value</code> to the requested
   * <code>type</code>.
   * 
   * @param <T>
   *        is the templated type that is requested.
   * @param type
   *        is the requested type.
   * @param value
   *        is the value to convert. May be <code>null</code>.
   * @return the <code>value</code> converted to the given <code>type</code>.
   *         Will be <code>null</code> if and only if the given
   *         <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the given <code>value</code> can NOT be converted to the given
   *         <code>type</code>.
   */
  protected abstract <T> T convertValue(Class<T> type, V value) throws WrongValueTypeException;

  /**
   * This method converts a given <code>value</code> to the templated type of
   * this implementation.
   * 
   * @param value
   *        is the value to convert. May be <code>null</code>.
   * @return the converted <code>value</code>. Will be <code>null</code> if
   *         and only if the given <code>value</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the given <code>value</code> can NOT be converted to the given
   *         <code>type</code>.
   */
  protected abstract V convertValue(Object value) throws WrongValueTypeException;

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    V value = getPlainValue();
    if (value == null) {
      return NULL_STRING;
    } else {
      return value.toString();
    }
  }

}
