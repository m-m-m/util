/* $Id$ */
package net.sf.mmm.value.api;

import java.util.Date;

import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.ValueOutOfRangeException;
import net.sf.mmm.value.api.WrongValueTypeException;

/**
 * This is the interface for a generic value.<br>
 * The actual value can be {@link #isEmpty() empty}. In that case the non-arg
 * getter methods (e.g. {@link #getObject()}) will throw a
 * {@link net.sf.mmm.value.api.ValueNotSetException}. Use the
 * {@link #isEmpty()} method to determine if the generic-value is empty.<br>
 * If possible the default-argument getter methods (e.g.
 * {@link #getObject(Object)}) should be used instead. Depending on
 * {@link #isAddDefaults()}, the supplied value is automatically assigned if
 * not <code>null</code> and the generic-value was {@link #isEmpty() empty}.<br>
 * For numeric access there is the generíc {@link #getNumber()} method and
 * methods for specific numeric types (e.g. {@link #getInteger()}). If you want
 * to get a numeric value allowing conversion use the {@link #getNumber()}
 * method. E.g. if you want to get a value as {@link Integer} even though it may
 * be a {@link Long} or {@link Double} use
 * <code>{@link #getNumber()}.{@link java.lang.Number#intValue() intValue()}</code>
 * instead {@link #getInteger()} to avoid a
 * {@link net.sf.mmm.value.api.WrongValueTypeException}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface GenericValue {

  /** the (default) String that represents the value <code>null</code> */
  String NULL_STRING = "NULL";

  /**
   * This method determines if this value is automatically adding defaults. This
   * means that if is currently {@link #isEmpty() empty} a supplied
   * {@link #getValue(Class, Object) default value} will automatically be
   * assigned as value if NOT <code>null</code>. <br>
   * 
   * @return <code>true</code> if this value is automatically adding defaults,
   *         <code>false</code> otherwise.
   */
  boolean isAddDefaults();

  /**
   * This method determines if this value is empty (has no actual value
   * assigned) and the non-arg getters like {@link #getString()} will throw an
   * {@link ValueNotSetException}.
   * 
   * @see #isAddDefaults()
   * 
   * @return <code>true</code> if this value is empty, <code>false</code>
   *         otherwise (if a value is assigned).
   */
  boolean isEmpty();

  /**
   * This method gets the value in a generic way. The value is required and
   * should NOT be {@link #isEmpty()}.
   * 
   * @see #getValue(Class, Object)
   * @see #isEmpty()
   * 
   * @param <T>
   *        is the templated type of the requested value type.
   * @param type
   *        is the class refelecting the requested value type.
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this object {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NOT compatible with the given <code>type</code>.
   */
  <T> T getValue(Class<T> type) throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value in a generic way. A default is supplied as
   * argument in case that the value is {@link #isEmpty() NOT available}.<br>
   * The requested type is specified by the given parameter <code>type</code>.
   * At least the following types must be supported:
   * <ul>
   * <li>{@link Object}</li>
   * <li>{@link String}</li>
   * <li>{@link Boolean}</li>
   * <li>{@link Class}</li>
   * <li>{@link Number}</li>
   * <li>{@link Integer}</li>
   * <li>{@link Long}</li>
   * <li>{@link Double}</li>
   * <li>{@link Float}</li>
   * <li>{@link Short}</li>
   * <li>{@link Byte}</li>
   * <li>{@link java.util.Date}</li>
   * </ul>
   * 
   * @see #isEmpty()
   * @see #isAddDefaults()
   * 
   * @param <T>
   *        is the templated type of the requested value type.
   * @param type
   *        is the class refelecting the requested value type.
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if the value
   *         {@link #isEmpty() is empty}. Will only be <code>null</code> if
   *         this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NOT compatible with the given <code>type</code>
   *         (see supported types above).
   */
  <T> T getValue(Class<T> type, T defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value as object. The typed methods such as
   * {@link #getString()} should be prefered to this method if possible.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if the value was NOT set.
   */
  Object getObject() throws ValueNotSetException;

  /**
   * This method gets the value as object. The typed methods such as
   * {@link #getString(String)} should be prefered to this method if possible.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   */
  Object getObject(Object defaultValue);

  /**
   * This method gets the value as string.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO string.
   */
  String getString() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value as string.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO string.
   */
  String getString(String defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value as boolean.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO boolean.
   */
  boolean getBoolean() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value as boolean.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO boolean.
   */
  Boolean getBoolean(Boolean defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value if it is a defined date value.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this object {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO date.
   */
  Date getDate() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value if it is a defined date value.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO date.
   */
  Date getDate(Date defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value as double.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO double.
   */
  double getDouble() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value if it is a double value.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO double.
   */
  Double getDouble(Double defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value as integer.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO integer.
   */
  int getInteger() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value as integer.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO integer.
   */
  Integer getInteger(Integer defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value as number.
   * 
   * @see #getValue(Class)
   * @see #getNumber(Number, Number)
   * @see #getInteger()
   * @see #getLong()
   * @see #getDouble()
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO number.
   */
  Number getNumber() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value as number.
   * 
   * @see #getValue(Class, Object)
   * @see #getNumber(Number, Number, Number)
   * @see #getInteger(int)
   * @see #getLong(long)
   * @see #getDouble(double)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO number.
   */
  Number getNumber(Number defaultValue) throws WrongValueTypeException;

  /**
   * This method gets a numeric value and also validates that it is in the given
   * range from <code>minimum</code> to <code>maximum</code>.
   * 
   * @see #getNumber()
   * 
   * @param <T>
   *        is the templated numeric value type.
   * @param minimum
   *        is the minimum number allowed. Use MIN_VALUE (e.g.
   *        {@link Double#MIN_VALUE}) if unbound.
   * @param maximum
   *        is the maximum number allowed. Use MAX_VALUE (e.g.
   *        {@link Long#MAX_VALUE}) if unbound.
   * @return the requested value in the given range from <code>minimum</code>
   *         and <code>maximum</code>.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO number.
   * @throws ValueOutOfRangeException
   *         if the value is NOT in the given range from <code>minimum</code>
   *         to <code>maximum</code>.
   */
  <T extends Number> T getNumber(T minimum, T maximum) throws ValueNotSetException,
      WrongValueTypeException, ValueOutOfRangeException;

  /**
   * This method gets a numeric value and also validates that it is in the given
   * range from <code>minimum</code> to <code>maximum</code>.
   * 
   * @see #getNumber(Number)
   * 
   * @param <T>
   *        is the templated numeric value type.
   * @param minimum
   *        is the minimum number allowed. Use MIN_VALUE (e.g.
   *        {@link Double#MIN_VALUE}) if unbound.
   * @param maximum
   *        is the maximum number allowed. Use MAX_VALUE (e.g.
   *        {@link Long#MAX_VALUE}) if unbound.
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>. Else it must be in the given range
   *        from <code>minimum</code> to <code>maximum</code>.
   * @return the requested value in the range of <code>minimum</code> and
   *         <code>maximum</code> or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO number.
   * @throws ValueOutOfRangeException
   *         if the value is NOT in the given range from <code>minimum</code>
   *         to <code>maximum</code>.
   */
  <T extends Number> T getNumber(T minimum, T maximum, T defaultValue)
      throws WrongValueTypeException, ValueOutOfRangeException;

  /**
   * This method gets the value as long.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO long.
   */
  long getLong() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value as long.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO long.
   */
  Long getLong(Long defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the value if it is a defined java class value.
   * 
   * @see #getValue(Class)
   * 
   * @return the requested value.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO class.
   */
  Class<?> getJavaClass() throws ValueNotSetException, WrongValueTypeException;

  /**
   * This method gets the value if it is a defined java class value.
   * 
   * @see #getValue(Class, Object)
   * 
   * @param defaultValue
   *        is the default returned if this value {@link #isEmpty() is empty}.
   *        It may be <code>null</code>.
   * @return the requested value or the <code>defaultValue</code> if this
   *         value {@link #isEmpty() is empty}. Will only be <code>null</code>
   *         if this value {@link #isEmpty() is empty} and the
   *         <code>defaultValue</code> is <code>null</code>.
   * @throws WrongValueTypeException
   *         if the value is NO class.
   */
  Class<?> getJavaClass(Class<?> defaultValue) throws WrongValueTypeException;

  /**
   * This method gets the java class using {@link #getJavaClass()} and creates a
   * new {@link Class#newInstance() instance} using the non-arg constructor. The
   * the resulting object must be an instance of the given type.
   * 
   * @see #getJavaClass()
   * 
   * @param <T>
   *        is the templated type of the requested instance.
   * @param superType
   *        is the expected (super-)interface or (super-)class of the instance.
   *        Use <code>Object.class</code> if any type is acceptable.
   * @return an instance of the value as java class.
   * @throws ValueNotSetException
   *         if this value {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO class or the java-class value does not implement
   *         the given super-type.
   * @throws ValueInstanciationException
   *         if the instantiation failed.
   */
  <T> T getJavaClassInstance(Class<T> superType) throws ValueNotSetException,
      WrongValueTypeException, ValueInstanciationException;

  /**
   * This method gets the java class using {@link #getJavaClass(Class)} and
   * creates a new {@link Class#newInstance() instance} using the non-arg
   * constructor. The the resulting object must be an instance of the given
   * type.<br>
   * The method behaves like
   * {@link #getJavaClassInstance(Class, Class, boolean)} with setDefault set to
   * <code>true</code>.
   * 
   * @see #getJavaClass(Class)
   * 
   * @param <T>
   *        is the templated type of the requested instance.
   * @param superType
   *        is the expected (super-)interface or (super-)class of the instance.
   *        Use <code>Object.class</code> if any type is acceptable.
   * @param defaultValue
   *        is the default used if this value {@link #isEmpty() is empty}.
   *        Unlike the other getters with a default value, this
   *        <code>defaultValue</code> must NOT be <code>null</code>.
   * @return an instance of the value as java class or the class given by
   *         <code>defaultValue</code> if this value
   *         {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO class or the java-class value does not implement
   *         the given super-type.
   * @throws ValueInstanciationException
   *         if the instantiation failed.
   */
  <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue)
      throws WrongValueTypeException, ValueInstanciationException;

  /**
   * This method gets the java class using {@link #getJavaClass(Class)} and
   * creates a new {@link Class#newInstance() instance} using the non-arg
   * constructor. The resulting object will be an instance of the given type.
   * 
   * @see #getJavaClass(Class)
   * 
   * @param <T>
   *        is the templated type of the requested instance.
   * @param superType
   *        is the expected (super-)interface or -class of the instance. Use
   *        <code>Object.class</code> if any type is acceptable. It is ensured
   *        that the resulting instance object can be casted to this type.
   * @param defaultValue
   *        is the default used if this value {@link #isEmpty() is empty}.
   *        Unlike the other getters with a default value, this
   *        <code>defaultValue</code> must NOT be <code>null</code>.
   * @param setDefault
   *        if <code>false</code> the flag {@link #isAddDefaults()} is
   *        ignored.
   * @return an instance of the value as java class or the class given by
   *         <code>defaultValue</code> if this value
   *         {@link #isEmpty() is empty}.
   * @throws WrongValueTypeException
   *         if the value is NO class or the java-class value does not implement
   *         the given super-type.
   * @throws ValueInstanciationException
   *         if the instantiation failed.
   */
  <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue, boolean setDefault)
      throws WrongValueTypeException, ValueInstanciationException;

}
