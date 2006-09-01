/* $ Id: $ */
package net.sf.mmm.value.base;

import java.util.Date;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotEditableException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.ValueOutOfRangeException;
import net.sf.mmm.value.api.WrongValueTypeException;
import net.sf.mmm.value.api.MutableGenericValueIF;

/**
 * This is the very abstract base implementation of the
 * {@link MutableGenericValueIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericValue implements MutableGenericValueIF {

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
     * @see net.sf.mmm.value.api.GenericValueIF#isAddDefaults() {@inheritDoc}
     */
    public boolean isAddDefaults() {

        return true;
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getObject() {@inheritDoc}
     */
    public Object getObject() throws ValueNotSetException {

        return getValue(Object.class);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getObject(java.lang.Object)
     *      {@inheritDoc}
     */
    public Object getObject(Object defaultValue) {

        return getValue(Object.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getString() {@inheritDoc}
     */
    public String getString() throws ValueNotSetException, WrongValueTypeException {

        return getValue(String.class);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getString(java.lang.String)
     *      {@inheritDoc}
     */
    public String getString(String defaultValue) throws WrongValueTypeException {

        return getValue(String.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getBoolean() {@inheritDoc}
     */
    public boolean getBoolean() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Boolean.class).booleanValue();
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getBoolean(java.lang.Boolean)
     *      {@inheritDoc}
     */
    public Boolean getBoolean(Boolean defaultValue) throws WrongValueTypeException {

        return getValue(Boolean.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getDate() {@inheritDoc}
     */
    public Date getDate() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Date.class);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getDate(java.util.Date)
     *      {@inheritDoc}
     */
    public Date getDate(Date defaultValue) throws WrongValueTypeException {

        return getValue(Date.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getDouble() {@inheritDoc}
     */
    public double getDouble() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Double.class).doubleValue();
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getDouble(java.lang.Double)
     *      {@inheritDoc}
     */
    public Double getDouble(Double defaultValue) throws WrongValueTypeException {

        return getValue(Double.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getInteger() {@inheritDoc}
     */
    public int getInteger() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Integer.class).intValue();
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getInteger(java.lang.Integer)
     *      {@inheritDoc}
     */
    public Integer getInteger(Integer defaultValue) throws WrongValueTypeException {

        return getValue(Integer.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getJavaClass() {@inheritDoc}
     */
    public Class<?> getJavaClass() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Class.class);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getJavaClass(java.lang.Class)
     *      {@inheritDoc}
     */
    public Class<?> getJavaClass(Class<?> defaultValue) throws WrongValueTypeException {

        return getValue(Class.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getLong() {@inheritDoc}
     */
    public long getLong() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Long.class).longValue();
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getLong(java.lang.Long)
     *      {@inheritDoc}
     */
    public Long getLong(Long defaultValue) throws WrongValueTypeException {

        return getValue(Long.class, defaultValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getNumber() {@inheritDoc}
     */
    public Number getNumber() throws ValueNotSetException, WrongValueTypeException {

        return getValue(Number.class);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getNumber(java.lang.Number)
     *      {@inheritDoc}
     */
    public Number getNumber(Number defaultValue) throws WrongValueTypeException {

        return getValue(Number.class, defaultValue);
    }

    /**
     * This method creates a new {@link Class#newInstance() instance} of the
     * given java-class using the non-arg constructor. The resulting object must
     * be an instance of the given super-type.
     * 
     * @param <T>
     *        is the templated type of the requested instance.
     * @param javaClass
     *        is the java-class to instantiate.
     * @param superType
     *        is the expected (super-)interface or -class of the instance. Use
     *        <code>Object.class</code> if any type is acceptable. It is
     *        ensured that the resulting instance object can be casted to this
     *        type.
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
     * @see net.sf.mmm.value.api.GenericValueIF#getJavaClassInstance(java.lang.Class)
     *      {@inheritDoc}
     */
    public <T> T getJavaClassInstance(Class<T> superType) throws ValueNotSetException,
            WrongValueTypeException, ValueInstanciationException {

        Class javaClass = getJavaClass();
        return createJavaClassInstance(javaClass, superType);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getJavaClassInstance(java.lang.Class,
     *      java.lang.Class) {@inheritDoc}
     */
    public <T> T getJavaClassInstance(Class<T> superType, Class<? extends T> defaultValue)
            throws WrongValueTypeException, ValueInstanciationException {

        return getJavaClassInstance(superType, defaultValue, true);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getJavaClassInstance(java.lang.Class,
     *      java.lang.Class, boolean) {@inheritDoc}
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
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setBoolean(boolean)
     *      {@inheritDoc}
     */
    public void setBoolean(boolean newValue) throws ValueNotEditableException,
            WrongValueTypeException {

        setObject(Boolean.valueOf(newValue));
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setDate(java.util.Date)
     *      {@inheritDoc}
     */
    public void setDate(Date newValue) throws ValueNotEditableException, WrongValueTypeException {

        setObject(newValue);
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setDouble(double)
     *      {@inheritDoc}
     */
    public void setDouble(double newValue) throws ValueNotEditableException,
            WrongValueTypeException {

        setObject(Double.valueOf(newValue));
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setInteger(int)
     *      {@inheritDoc}
     */
    public void setInteger(int newValue) throws ValueNotEditableException, WrongValueTypeException {

        setObject(Integer.valueOf(newValue));
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setJavaClass(java.lang.Class)
     *      {@inheritDoc}
     */
    public void setJavaClass(Class newValue) throws ValueNotEditableException,
            WrongValueTypeException {

        setObject(newValue);
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#setString(java.lang.String)
     *      {@inheritDoc}
     */
    public void setString(String newValue) throws ValueNotEditableException,
            WrongValueTypeException {

        setObject(newValue);
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getNumber(java.lang.Number,
     *      java.lang.Number) {@inheritDoc}
     */
    public <T extends Number> T getNumber(T minimum, T maximum) throws ValueNotSetException,
            WrongValueTypeException, ValueOutOfRangeException {

        T value = getValue(minimum.getClass());
        if ((minimum.doubleValue() > value.doubleValue())
                || (maximum.doubleValue() < value.doubleValue())) {
            throw new ValueOutOfRangeException(this, value, minimum, maximum);
        }
        return value;
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#getNumber(java.lang.Number,
     *      java.lang.Number, java.lang.Number) {@inheritDoc}
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
