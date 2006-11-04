/* $ Id: $ */
package net.sf.mmm.value.base;

import java.util.Date;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotEditableException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.ValueOutOfRangeException;
import net.sf.mmm.value.api.WrongValueTypeException;
import net.sf.mmm.value.api.MutableGenericValue;

/**
 * This is the very abstract base implementation of the
 * {@link MutableGenericValue} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericValue implements MutableGenericValue {

  /**
   * The constructor.
   */
  public AbstractGenericValue() {

    super();
  }

  /**
   * This implementation returns <code>true</code> as default. Override to
   * change.
   * 
   * @see net.sf.mmm.value.api.GenericValue#isAddDefaults()
   */
  public boolean isAddDefaults() {

    return true;
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getObject()
   */
  public Object getObject() throws ValueNotSetException {

    return getValue(Object.class);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getObject(java.lang.Object)
   * 
   */
  public Object getObject(Object defaultValue) {

    return getValue(Object.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getString()
   */
  public String getString() throws ValueNotSetException, WrongValueTypeException {

    return getValue(String.class);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getString(java.lang.String)
   * 
   */
  public String getString(String defaultValue) throws WrongValueTypeException {

    return getValue(String.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getBoolean()
   */
  public boolean getBoolean() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Boolean.class).booleanValue();
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getBoolean(java.lang.Boolean)
   * 
   */
  public Boolean getBoolean(Boolean defaultValue) throws WrongValueTypeException {

    return getValue(Boolean.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getDate()
   */
  public Date getDate() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Date.class);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getDate(java.util.Date)
   * 
   */
  public Date getDate(Date defaultValue) throws WrongValueTypeException {

    return getValue(Date.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getDouble()
   */
  public double getDouble() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Double.class).doubleValue();
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getDouble(java.lang.Double)
   * 
   */
  public Double getDouble(Double defaultValue) throws WrongValueTypeException {

    return getValue(Double.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getInteger()
   */
  public int getInteger() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Integer.class).intValue();
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getInteger(java.lang.Integer)
   * 
   */
  public Integer getInteger(Integer defaultValue) throws WrongValueTypeException {

    return getValue(Integer.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getJavaClass()
   */
  public Class<?> getJavaClass() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Class.class);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getJavaClass(java.lang.Class)
   * 
   */
  public Class<?> getJavaClass(Class<?> defaultValue) throws WrongValueTypeException {

    return getValue(Class.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getLong()
   */
  public long getLong() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Long.class).longValue();
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getLong(java.lang.Long)
   * 
   */
  public Long getLong(Long defaultValue) throws WrongValueTypeException {

    return getValue(Long.class, defaultValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getNumber()
   */
  public Number getNumber() throws ValueNotSetException, WrongValueTypeException {

    return getValue(Number.class);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getNumber(java.lang.Number)
   */
  public Number getNumber(Number defaultValue) throws WrongValueTypeException {

    return getValue(Number.class, defaultValue);
  }

  /**
   * This method creates a new {@link Class#newInstance() instance} of the given
   * java-class using the non-arg constructor. The resulting object must be an
   * instance of the given super-type.
   * 
   * @param <T>
   *        is the templated type of the requested instance.
   * @param javaClass
   *        is the java-class to instantiate.
   * @param superType
   *        is the expected (super-)interface or -class of the instance. Use
   *        <code>Object.class</code> if any type is acceptable. It is ensured
   *        that the resulting instance object can be casted to this type.
   * @return an instance of the given java-class.
   * @throws WrongValueTypeException
   *         if the java-class value does not implement the given super-type.
   * @throws ValueInstanciationException
   *         if the instantiation failed.
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
   * @see net.sf.mmm.value.api.GenericValue#getJavaClassInstance(java.lang.Class)
   * 
   */
  public <T> T getJavaClassInstance(Class<T> superType) throws ValueNotSetException,
      WrongValueTypeException, ValueInstanciationException {

    Class javaClass = getJavaClass();
    return createJavaClassInstance(javaClass, superType);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getJavaClassInstance(java.lang.Class,
   *      java.lang.Class)
   */
  public <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue)
      throws WrongValueTypeException, ValueInstanciationException {

    return getJavaClassInstance(superType, defaultValue, true);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getJavaClassInstance(java.lang.Class,
   *      java.lang.Class, boolean)
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
   * @see net.sf.mmm.value.api.MutableGenericValue#setBoolean(boolean)
   * 
   */
  public void setBoolean(boolean newValue) throws ValueNotEditableException,
      WrongValueTypeException {

    setObject(Boolean.valueOf(newValue));
  }

  /**
   * @see net.sf.mmm.value.api.MutableGenericValue#setDate(java.util.Date)
   * 
   */
  public void setDate(Date newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * @see net.sf.mmm.value.api.MutableGenericValue#setDouble(double)
   * 
   */
  public void setDouble(double newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(Double.valueOf(newValue));
  }

  /**
   * @see net.sf.mmm.value.api.MutableGenericValue#setInteger(int)
   * 
   */
  public void setInteger(int newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(Integer.valueOf(newValue));
  }

  /**
   * @see net.sf.mmm.value.api.MutableGenericValue#setJavaClass(java.lang.Class)
   * 
   */
  public void setJavaClass(Class newValue) throws ValueNotEditableException,
      WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * @see net.sf.mmm.value.api.MutableGenericValue#setString(java.lang.String)
   * 
   */
  public void setString(String newValue) throws ValueNotEditableException, WrongValueTypeException {

    setObject(newValue);
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getNumber(java.lang.Number,
   *      java.lang.Number)
   */
  public <T extends Number> T getNumber(T minimum, T maximum) throws ValueNotSetException,
      WrongValueTypeException, ValueOutOfRangeException {

    Class<T> type = (Class<T>) minimum.getClass();
    T value = getValue(type);
    if ((minimum.doubleValue() > value.doubleValue())
        || (maximum.doubleValue() < value.doubleValue())) {
      throw new ValueOutOfRangeException(this, value, minimum, maximum);
    }
    return value;
  }

  /**
   * @see net.sf.mmm.value.api.GenericValue#getNumber(java.lang.Number,
   *      java.lang.Number, java.lang.Number)
   */
  public <T extends Number> T getNumber(T minimum, T maximum, T defaultValue)
      throws WrongValueTypeException, ValueOutOfRangeException {

    Class<T> type = (Class<T>) minimum.getClass();
    T value = getValue(type, defaultValue);
    if (value == null) {
      return null;
    }
    if ((minimum.doubleValue() > value.doubleValue())
        || (maximum.doubleValue() < value.doubleValue())) {
      throw new ValueOutOfRangeException(this, value, minimum, maximum);
    }
    return value;
  }

  /**
   * This method parses an numberic value.
   * 
   * @param numberValue
   *        is the number value as string.
   * @return the value as number.
   * @throws WrongValueTypeException
   *         if the given string is no number.
   */
  protected Number parseNumber(String numberValue) throws WrongValueTypeException {

    try {
      Double d = Double.valueOf(numberValue);
      return NumericUtil.toSimplestNumber(d);
    } catch (NumberFormatException e) {
      throw new WrongValueTypeException(this, Number.class, e);
    }
  }

}
