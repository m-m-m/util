/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate.usertype;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.math.base.MathUtilImpl;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.value.api.ValueConvertException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * This is the abstract base implementation of
 * {@link org.hibernate.usertype.UserType} to map a custom {@link Datatype}.
 * 
 * @param <V> the generic for the basic java type representing the
 *        {@link Datatype#getValue() value}.
 * @param <T> the generic for the adapted {@link Datatype}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DatatypeUserType<V, T extends Datatype<V>> extends AbstractUserType<T> {

  /** @see #getValueClass() */
  private final Class<V> valueClass;

  /** @see #getMathUtil() */
  private MathUtil mathUtil;

  /**
   * The constructor.
   * 
   * @param sqlType is the {@link #sqlTypes() SQL type} used to store the
   *        adapted {@link Datatype}.
   * @param datatype is the {@link #returnedClass() java class} representing the
   *        adapted {@link Datatype}.
   * @param valueClass is the {@link #getValueClass() value class} reflecting
   *        the {@link Datatype#getValue() basic java value}.
   */
  public DatatypeUserType(int sqlType, Class<T> datatype, Class<V> valueClass) {

    super(sqlType, datatype);
    this.valueClass = valueClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.mathUtil == null) {
      this.mathUtil = MathUtilImpl.getInstance();
    }
  }

  /**
   * @return the mathUtil
   */
  protected MathUtil getMathUtil() {

    if (this.mathUtil == null) {
      initialize();
    }
    return this.mathUtil;
  }

  /**
   * This method injects the {@link MathUtil}.
   * 
   * @param mathUtil is the {@link MathUtil} instance.
   */
  @Inject
  public void setMathUtil(MathUtil mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  /**
   * This method gets the {@link Class} reflecting the
   * {@link Datatype#getValue() basic java value} of the adapted
   * {@link Datatype}.
   * 
   * @return the value class.
   */
  protected final Class<V> getValueClass() {

    return this.valueClass;
  }

  /**
   * This method converts the given <code>value</code> from {@link Object} to
   * the {@link #getValueClass() value class}.
   * 
   * @param value is the value as retrieved from hibernate.
   * @return the value cast or converted to the {@link #getValueClass() value
   *         class}.
   */
  protected V toValue(Object value) {

    if (this.valueClass.isInstance(value)) {
      return this.valueClass.cast(value);
    } else if (Number.class.isAssignableFrom(this.valueClass)) {
      NumberType<? extends Number> numberType = getMathUtil().getNumberType(this.valueClass);
      Number result = numberType.valueOf((Number) value, true);
      return this.valueClass.cast(result);
    } else if (String.class.equals(this.valueClass)) {
      return this.valueClass.cast(value.toString());
    } else {
      throw new ValueConvertException(value, this.valueClass);
    }
  }

  /**
   * This method converts the value given as <code>value</code> to the custom
   * {@link Datatype}.
   * 
   * @param value is the basic java type representing the {@link Datatype}.
   * @return the converted {@link Datatype}.
   */
  protected T toDatatype(V value) {

    if (returnedClass().isEnum()) {
      for (T enumValue : returnedClass().getEnumConstants()) {
        if (value == null) {
          if (enumValue.getValue() == null) {
            return enumValue;
          }
        } else if (value.equals(enumValue.getValue())) {
          return enumValue;
        }
      }
      throw new IllegalCaseException(returnedClass().getSimpleName() + "." + value);
    } else {
      Constructor<T> constructor = null;
      try {
        constructor = returnedClass().getConstructor(getValueClass());
        return constructor.newInstance(value);
      } catch (IllegalArgumentException e) {
        throw new NlsIllegalArgumentException(value, e);
      } catch (SecurityException e) {
        throw new InstantiationFailedException(e, returnedClass());
      } catch (InstantiationException e) {
        throw new InstantiationFailedException(e, returnedClass());
      } catch (IllegalAccessException e) {
        throw new AccessFailedException(e, constructor);
      } catch (InvocationTargetException e) {
        throw new InstantiationFailedException(e, returnedClass());
      } catch (NoSuchMethodException e) {
        throw new InstantiationFailedException(e, returnedClass());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
      throws SQLException {

    assert (rs != null);
    assert (names != null);
    if (names.length != 1) {
      throw new NlsIllegalArgumentException(Integer.valueOf(names.length), "names.length");
    }
    Object result = rs.getObject(names[0]);
    if (rs.wasNull()) {
      return null;
    } else {
      return toDatatype(toValue(result));
    }
  }

  /**
   * This method converts the given {@link Datatype} <code>value</code> to the
   * according {@link #sqlTypes() SQL type}.
   * 
   * @param value is the {@link Datatype} instance to convert.
   * @return the given <code>value</code> as {@link #sqlTypes() SQL type}.
   */
  public Object toSqlType(T value) {

    return value.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
      throws HibernateException, SQLException {

    if (value == null) {
      st.setNull(index, sqlTypes()[0]);
    } else {
      st.setObject(index, toSqlType((T) value));
    }

  }

}
