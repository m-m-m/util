/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import net.sf.mmm.util.component.api.NotInitializedException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;

/**
 * This is the abstract base implementation of {@link UserType}.
 *
 * @see org.hibernate.annotations.Type
 *
 * @param <T> the generic for the adapted datatype.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public abstract class AbstractUserType<T> extends AbstractLoggableComponent implements UserType {

  /** @see #sqlTypes */
  private final int[] sqlTypes;

  /** @see #returnedClass() */
  private final Class<T> javaType;

  /**
   * The constructor.
   *
   * @param sqlType is the {@link #sqlTypes() SQL type} used to store the adapted datatype.
   * @param javaType is the {@link #returnedClass() java class} representing the adapted datatype.
   */
  public AbstractUserType(int sqlType, Class<T> javaType) {

    super();
    this.sqlTypes = new int[] { sqlType };
    this.javaType = javaType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Logger getLogger() throws NotInitializedException {

    // hack to allow non component user-types
    // e.g. if used via @UserType
    if (!getInitializationState().isInitialized()) {
      initialize();
    }
    return super.getLogger();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[] sqlTypes() {

    return this.sqlTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> returnedClass() {

    return this.javaType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object x, Object y) throws HibernateException {

    if (x == null) {
      return (y == null);
    } else {
      return x.equals(y);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode(Object x) throws HibernateException {

    if (x != null) {
      return x.hashCode();
    }
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract T nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
      throws SQLException;

  /**
   * {@inheritDoc}
   */
  @Override
  public Object deepCopy(Object value) throws HibernateException {

    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMutable() {

    // datatypes should be immutable
    // otherwise this method has to be overridden
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Serializable disassemble(Object value) throws HibernateException {

    return (Serializable) value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {

    return cached;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {

    return original;
  }

  /**
   * Gets the corresponding {@link Types sql type} for the given java {@link Class}.
   *
   * @param standardDatatype a {@link Class} reflecting a
   *        {@link net.sf.mmm.util.lang.api.DatatypeDetector#isJavaStandardDatatype(Class) standard java}
   *        datatype (or anything else).
   * @return the {@link Types sql type} corresponding to the given {@link Class} or <code>null</code> if the
   *         type is no {@link net.sf.mmm.util.lang.api.DatatypeDetector#isJavaStandardDatatype(Class)
   *         standard java}.
   */
  public static Integer getSqlType(Class<?> standardDatatype) {

    int result;
    if (standardDatatype == String.class) {
      result = Types.VARCHAR;
    } else if ((standardDatatype == Integer.class) || (standardDatatype == int.class)) {
      result = Types.INTEGER;
    } else if ((standardDatatype == Long.class) || (standardDatatype == long.class)) {
      result = Types.BIGINT;
    } else if ((standardDatatype == Boolean.class) || (standardDatatype == boolean.class)) {
      result = Types.BOOLEAN;
    } else if ((standardDatatype == Double.class) || (standardDatatype == double.class)) {
      result = Types.DOUBLE;
    } else if ((standardDatatype == Float.class) || (standardDatatype == float.class)) {
      result = Types.REAL; // not Types.FLOAT !
    } else if ((standardDatatype == Short.class) || (standardDatatype == short.class)) {
      result = Types.SMALLINT;
    } else if ((standardDatatype == Byte.class) || (standardDatatype == byte.class)) {
      // TINYINT is unsigned [0..255] and cannot represent negative values as byte is signed [-128,172]
      // result = Types.SMALLINT;
      result = Types.TINYINT;
    } else if (standardDatatype == BigDecimal.class) {
      result = Types.DECIMAL;
    } else if (standardDatatype == BigInteger.class) {
      result = Types.BIGINT;
    } else if (standardDatatype == Timestamp.class) {
      result = Types.TIMESTAMP;
    } else if (standardDatatype == Time.class) {
      result = Types.TIME;
    } else if (standardDatatype == Date.class) {
      result = Types.DATE;
    } else if (standardDatatype == java.sql.Date.class) {
      result = Types.DATE;
    } else if ((standardDatatype == Character.class) || (standardDatatype == char.class)) {
      result = Types.CHAR;
    } else {
      return null;
    }
    return Integer.valueOf(result);
  }

}
