/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import java.util.Date;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.util.value.ValueNotSetException;
import net.sf.mmm.util.value.ValueOutOfRangeException;
import net.sf.mmm.util.value.WrongValueTypeException;
import net.sf.mmm.value.api.MutableGenericValue;
import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotEditableException;

/**
 * This is the very abstract base implementation of the
 * {@link MutableGenericValue} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericValue implements MutableGenericValue {

  /** @see #isSupported(Class) */
  private static final Class<?>[] SUPPORTED_CLASSES = new Class[] { Object.class, String.class,
      Boolean.class, boolean.class, Class.class, Number.class, Integer.class, int.class,
      Long.class, long.class, Double.class, double.class, Float.class, float.class, Short.class,
      short.class, Byte.class, byte.class, Date.class };

  /**
   * The constructor.
   */
  public AbstractGenericValue() {

    super();
  }

  /**
   * This method determines if the given <code>valueType</code> is supported
   * by {@link #getValue(Class)}. The term supported here means that
   * {@link #getValue(Class)} will potentially be able to convert the actual
   * value to <code>valueType</code>. You can use this method to avoid
   * (reduce) {@link WrongValueTypeException}s.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param valueType is the type to check.
   * @return <code>true</code> if the given <code>type</code> is supported,
   *         <code>false</code> otherwise.
   */
  public static boolean isSupported(Class<?> valueType) {

    for (Class<?> supportedType : SUPPORTED_CLASSES) {
      if (supportedType.equals(valueType)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   * 
   * This implementation returns <code>true</code> as default. Override to
   * change.
   */
  public boolean isAddDefaults() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public Object getObject() throws ValueNotSetException {

    return getValue(Object.class);
  }

  /**
   * {@inheritDoc}
   */
  public Object getObject(Object defaultValue) {

    return getValue(Object.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public String getString() throws ValueNotSetException, WrongValueTypeException {

    return getValue(String.class);
  }

  /**
   * {@inheritDoc}
   */
  public String getString(String defaultValue) throws WrongValueTypeException {

    return getValue(String.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public char getCharacter() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Character.class).charValue();
  }

  /**
   * {@inheritDoc}
   */
  public Character getCharacter(Character defaultValue) throws WrongValueTypeException {

    return getValue(Character.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public boolean getBoolean() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Boolean.class).booleanValue();
  }

  /**
   * {@inheritDoc}
   */
  public Boolean getBoolean(Boolean defaultValue) throws WrongValueTypeException {

    return getValue(Boolean.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public boolean getBoolean(boolean defaultValue) throws WrongValueTypeException {

    return getValue(Boolean.class, Boolean.valueOf(defaultValue)).booleanValue();
  }

  /**
   * {@inheritDoc}
   */
  public Date getDate() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Date.class);
  }

  /**
   * {@inheritDoc}
   */
  public Date getDate(Date defaultValue) throws WrongValueTypeException {

    return getValue(Date.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public double getDouble() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Double.class).doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  public Double getDouble(Double defaultValue) throws WrongValueTypeException {

    return getValue(Double.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public int getInteger() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Integer.class).intValue();
  }

  /**
   * {@inheritDoc}
   */
  public Integer getInteger(Integer defaultValue) throws WrongValueTypeException {

    return getValue(Integer.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getJavaClass() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Class.class);
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getJavaClass(Class<?> defaultValue) throws WrongValueTypeException {

    return getValue(Class.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public long getLong() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Long.class).longValue();
  }

  /**
   * {@inheritDoc}
   */
  public Long getLong(Long defaultValue) throws WrongValueTypeException {

    return getValue(Long.class, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public Number getNumber() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Number.class);
  }

  /**
   * {@inheritDoc}
   */
  public Number getNumber(Number defaultValue) throws WrongValueTypeException {

    return getValue(Number.class, defaultValue);
  }

  /**
   * This method creates a new {@link Class#newInstance() instance} of the given
   * java-class using the non-arg constructor. The resulting object must be an
   * instance of the given super-type.
   * 
   * @param <T> is the templated type of the requested instance.
   * @param javaClass is the java-class to instantiate.
   * @param superType is the expected (super-)interface or -class of the
   *        instance. Use <code>Object.class</code> if any type is acceptable.
   *        It is ensured that the resulting instance object can be casted to
   *        this type.
   * @return an instance of the given java-class.
   * @throws WrongValueTypeException if the java-class value does not implement
   *         the given super-type.
   * @throws ValueInstanciationException if the instantiation failed.
   */
  @SuppressWarnings("unchecked")
  protected <T> T createJavaClassInstance(Class<?> javaClass, Class<T> superType)
      throws WrongValueTypeException, ValueInstanciationException {

    try {
      if (!superType.isAssignableFrom(javaClass)) {
        throw new WrongValueTypeException(this, superType);
      }
      T result = (T) javaClass.newInstance();
      return result;
    } catch (InstantiationException e) {
      throw new ValueInstanciationException(javaClass, e);
    } catch (IllegalAccessException e) {
      throw new ValueInstanciationException(javaClass, e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getJavaClassInstance(Class<T> superType) throws ValueNotSetException,
      WrongValueTypeException, ValueInstanciationException {

    Class<?> javaClass = getJavaClass();
    return createJavaClassInstance(javaClass, superType);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue)
      throws WrongValueTypeException, ValueInstanciationException {

    return getJavaClassInstance(superType, defaultValue, true);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue,
      boolean setDefault) throws WrongValueTypeException, ValueInstanciationException {

    Class<?> javaClass;
    if (setDefault || !isEmpty()) {
      javaClass = getJavaClass(defaultValue);
    } else {
      javaClass = defaultValue;
    }
    return createJavaClassInstance(javaClass, superType);
  }

  /**
   * {@inheritDoc}
   */
  public void setBoolean(boolean newValue) throws ValueNotEditableException,
      WrongValueTypeException {

    setObject(Boolean.valueOf(newValue));
  }

  /**
   * {@inheritDoc}
   */
  public void setDate(Date newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * {@inheritDoc}
   */
  public void setDouble(double newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(Double.valueOf(newValue));
  }

  /**
   * {@inheritDoc}
   */
  public void setInteger(int newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(Integer.valueOf(newValue));
  }

  /**
   * {@inheritDoc}
   */
  public void setJavaClass(Class<?> newValue) throws ValueNotEditableException,
      WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * {@inheritDoc}
   */
  public void setString(String newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T extends Number> T getNumber(T minimum, T maximum) throws ValueNotSetException,
      WrongValueTypeException, ValueOutOfRangeException {

    Class<T> type = (Class<T>) minimum.getClass();
    T value = getValue(type);
    if ((minimum.doubleValue() > value.doubleValue())
        || (maximum.doubleValue() < value.doubleValue())) {
      throw new ValueOutOfRangeException(value, this, minimum, maximum);
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T extends Number> T getNumber(T minimum, T maximum, T defaultValue)
      throws WrongValueTypeException, ValueOutOfRangeException {

    Class<T> type = (Class<T>) minimum.getClass();
    T value = getValue(type, defaultValue);
    if (value == null) {
      return null;
    }
    if ((minimum.doubleValue() > value.doubleValue())
        || (maximum.doubleValue() < value.doubleValue())) {
      throw new ValueOutOfRangeException(value, this, minimum, maximum);
    }
    return value;
  }

  /**
   * This method parses a numeric value.
   * 
   * @param numberValue is the number value as string.
   * @return the value as number.
   * @throws WrongValueTypeException if the given string is no number.
   */
  protected Number parseNumber(String numberValue) throws WrongValueTypeException {

    try {
      Double d = Double.valueOf(numberValue);
      return NumericUtil.INSTANCE.toSimplestNumber(d);
    } catch (NumberFormatException e) {
      throw new WrongValueTypeException(this, Number.class, e);
    }
  }

}
