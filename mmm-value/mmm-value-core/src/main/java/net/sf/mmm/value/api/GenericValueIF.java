/* $Id: GenericValueIF.java 208 2006-08-22 20:29:11Z hohwille $ */
package net.sf.mmm.value.api;

import java.util.Date;

import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.ValueOutOfRangeException;
import net.sf.mmm.value.api.WrongValueTypeException;
import net.sf.mmm.xml.api.XmlSerializableIF;

/**
 * This is the interface for a generic value.<br>
 * It can be empty meaning that it has no value assigned. In that case the
 * non-arg getter methods (e.g. {@link #getObject()}) will throw a
 * {@link net.sf.mmm.value.api.ValueNotSetException}. Use the
 * {@link #hasValue()} method to determine if the generic-value is empty.<br>
 * If possible the default-argument getter methods (e.g.
 * {@link #getObject(Object)}) should be used instead. Depending on the
 * implementation, these methods may also assign the default value if the
 * generic-value was empty.<br>
 * For numeric access there is the generíc {@link #getNumber()} method and
 * methods for specific numeric types (e.g. {@link #getInteger()}). If you want
 * to get a numeric value allowing conversion use the {@link #getNumber()}
 * method. E.g. if you want to get a value as {@link Integer} even though it may
 * be a {@link Long} or {@link Double} use
 * <code>{@link #getNumber()}.{@link java.lang.Number#intValue() intValue()}</code>
 * instead {@link #getInteger()} to avoid a
 * {@link net.sf.mmm.value.api.WrongValueTypeException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface GenericValueIF extends XmlSerializableIF {

    /** the tag name used to represent a value as XML */
    String XML_TAG_VALUE = "value";

    /**
     * the attribute name used to represent the {@link Object#getClass() class}
     * of the value.
     */
    String XML_ATR_VALUE_CLASS = "class";

    /** the tag name used to represent the value <code>null</code> as XML */
    String XML_TAG_NULL = "null";

    /** the (default) String that represents the value <code>null</code> */
    String NULL_STRING = "NULL";

    /**
     * This method determines if this value is automatically adding defaults.
     * This means that if is currently {@link GenericValueIF#hasValue() empty} a
     * supplied {@link #getString(String) default value} will automatically be
     * assigned as value. <br>
     * 
     * @return <code>true</code> if this value is automatically adding
     *         defaults, <code>false</code> otherwise.
     */
    boolean isAddDefaults();

    /**
     * This method determines if this node has a value assigned and the non-arg
     * getters like {@link #getString()} will NOT throw an
     * {@link ValueNotSetException}. After a getter with argument is invoked
     * with a value other than <code>null</code> a value is set.
     * 
     * @return <code>true</code> if this node has a value, <code>false</code>
     *         otherwise.
     */
    boolean hasValue();

    /**
     * This method gets the value as object. The typed methods such as
     * {@link #getString()} should be prefered to this method if possible.
     * 
     * @see #hasValue()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     */
    Object getObject() throws ValueNotSetException;

    /**
     * This method gets the value as object. The typed methods such as
     * {@link #getString(String)} should be prefered to this method if possible.
     * 
     * @see #hasValue()
     * 
     * @param defaultValue
     *        is the value returned if this node currently has NO value
     *        {@link #hasValue() set}. The given default may be
     *        <code>null</code>. Then the value of this node will NOT be
     *        modified (will NOT be set if empty).
     * @return the value.
     */
    Object getObject(Object defaultValue);

    /**
     * This method gets the value in a generic way.
     * 
     * @see #getValue(Class, Object)
     * @see #hasValue()
     * 
     * @param <T>
     *        is the templated type of the requested value type.
     * @param type
     *        is the class refelecting the requested value type.
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NOT compatible with the given <code>type</code>.
     */
    <T> T getValue(Class<T> type) throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value in a generic way. Therefore the requested type
     * is specified by the given parameter <code>type</code>. At least the
     * following types must be supported:
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
     * @see #hasValue()
     * 
     * @param <T>
     *        is the templated type of the requested value type.
     * @param type
     *        is the class refelecting the requested value type.
     * @param defaultValue
     *        is the value returned if this node currently has NO value
     *        {@link #hasValue() set}. The given default may be
     *        <code>null</code>. Then the value of this node will NOT be
     *        modified (will NOT be set if empty).
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NOT compatible with the given <code>type</code>
     *         (see supported types above).
     */
    <T> T getValue(Class<T> type, T defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as string.
     * 
     * @see #hasValue()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO string.
     */
    String getString() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value as string.
     * 
     * @param defaultValue
     *        is the value set and returned if this node currently has NO value
     *        {@link #hasValue() set}. The given default may be
     *        <code>null</code>. Then the value of this node will NOT be
     *        modified (will NOT be set if empty).
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO string.
     */
    String getString(String defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as boolean.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO boolean.
     */
    boolean getBoolean() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value as boolean.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the value set and returned if the value was NOT set.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO boolean.
     */
    Boolean getBoolean(Boolean defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value if it is a defined date value.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO date.
     */
    Date getDate() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value if it is a defined date value.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the default value as described in {@link #getString(String)}.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO date.
     */
    Date getDate(Date defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as double.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO double.
     */
    double getDouble() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value if it is a double value.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the value returned if the value was NOT set.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO double.
     */
    Double getDouble(Double defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as integer.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO integer.
     */
    int getInteger() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value as integer.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the value returned if the value was NOT set.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO integer.
     */
    Integer getInteger(Integer defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as number.
     * 
     * @see #getInteger()
     * @see #getLong()
     * @see #getDouble()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO number.
     */
    Number getNumber() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value as number.
     * 
     * @see #getInteger(int)
     * @see #getLong(long)
     * @see #getDouble(double)
     * 
     * @param defaultValue
     *        is the value returned if the value was NOT set.
     * @return the value.
     * @throws WrongValueTypeException
     *         if the value is NO number.
     */
    Number getNumber(Number defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value as long.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO long.
     */
    long getLong() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value as long.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the value returned if the value was NOT set.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO long.
     */
    Long getLong(Long defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the value if it is a defined java class value.
     * 
     * @see #getString()
     * 
     * @return the value.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO class.
     */
    Class<?> getJavaClass() throws ValueNotSetException, WrongValueTypeException;

    /**
     * This method gets the value if it is a defined java class value.
     * 
     * @see #getString(String)
     * 
     * @param defaultValue
     *        is the default value as described in {@link #getString(String)}.
     * @return the value (or the defaultValue if value was NOT set).
     * @throws WrongValueTypeException
     *         if the value is NO class.
     */
    Class<?> getJavaClass(Class<?> defaultValue) throws WrongValueTypeException;

    /**
     * This method gets the java class using {@link #getJavaClass()} and creates
     * a new {@link Class#newInstance() instance} using the non-arg constructor.
     * The the resulting object must be an instance of the given type.
     * 
     * @see #getJavaClass()
     * 
     * @param <T>
     *        is the templated type of the requested instance.
     * @param superType
     *        is the expected (super-)interface or (super-)class of the
     *        instance. Use <code>Object.class</code> if any type is
     *        acceptable.
     * @return an instance of the value as java class.
     * @throws ValueNotSetException
     *         if the value was NOT set.
     * @throws WrongValueTypeException
     *         if the value is NO class or the java-class value does not
     *         implement the given super-type.
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
     * {@link #getJavaClassInstance(Class, Class, boolean)} with setDefault set
     * to <code>true</code>.
     * 
     * @see #getJavaClass(Class)
     * 
     * @param <T>
     *        is the templated type of the requested instance.
     * @param superType
     *        is the expected (super-)interface or (super-)class of the
     *        instance. Use <code>Object.class</code> if any type is
     *        acceptable.
     * @param defaultValue
     *        is the value set and returned if this node currently has NO value
     *        {@link #hasValue() set}. Unlike the other getters with default
     *        value, this defaultValue must NOT be <code>null</code>.
     * @return an instance of the value as java class.
     * @throws WrongValueTypeException
     *         if the value is NO class or the java-class value does not
     *         implement the given super-type.
     * @throws ValueInstanciationException
     *         if the instantiation failed.
     */
    <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue)
            throws WrongValueTypeException, ValueInstanciationException;

    /**
     * This method gets the java class using {@link #getJavaClass(Class)} and
     * creates a new {@link Class#newInstance() instance} using the non-arg
     * constructor. The the resulting object must be an instance of the given
     * type.
     * 
     * @see #getJavaClass(Class)
     * 
     * @param <T>
     *        is the templated type of the requested instance.
     * @param superType
     *        is the expected (super-)interface or -class of the instance. Use
     *        <code>Object.class</code> if any type is acceptable. It is
     *        ensured that the resulting instance object can be casted to this
     *        type.
     * @param defaultValue
     *        is the value set and returned if this node currently has NO value
     *        {@link #hasValue() set}. Unlike the other getters with default
     *        value, this defaultValue must NOT be <code>null</code>.
     * @param setDefault
     *        if <code>false</code> the defaultValue will NOT be set if the
     *        node currently {@link #hasValue() has} NO value, if
     *        <code>true</code> it will be set in that case.
     * @return an instance of the value as java class.
     * @throws WrongValueTypeException
     *         if the value is NO class or the java-class value does not
     *         implement the given super-type.
     * @throws ValueInstanciationException
     *         if the instantiation failed.
     */
    <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue,
            boolean setDefault) throws WrongValueTypeException, ValueInstanciationException;

    /**
     * This method validates if the given value is inside a specific range.<br>
     * The following code snipplet illustrates how to use this method:
     * 
     * <pre>
     * int port = value.getInteger(8080);
     * value.validateRange(port, 8000, 9000);
     * </pre>
     * 
     * A port number is read from the configuration. If the port number is not
     * defined the default value <code>8080</code> is used. Further it is
     * verified, that the resulting port number is in the range of [8000, 9000],
     * meaning that it is greater or equal to <code>8000</code> and less or
     * equal to <code>9000</code>.
     * 
     * @param value
     *        is the value to check.
     * @param minimum
     *        is the minimum value allowed.
     * @param maximum
     *        is the maximum value allowed.
     * @throws ValueOutOfRangeException
     *         if the given value is less than the minimum or greater than the
     *         maximum.
     */
    void validateRange(Number value, Number minimum, Number maximum)
            throws ValueOutOfRangeException;

}
