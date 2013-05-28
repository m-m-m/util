/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * This is the implementation of the {@link ReflectionUtilLimited} interface. It is strictly GWT compatible.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ReflectionUtilLimitedImpl extends AbstractLoggableComponent implements ReflectionUtilLimited {

  /** @see #getInstance() */
  private static final ReflectionUtilLimited INSTANCE = new ReflectionUtilLimitedImpl();

  /**
   * The constructor.
   */
  public ReflectionUtilLimitedImpl() {

    super();
  }

  /**
   * @return the instance
   */
  public static ReflectionUtilLimited getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getNonPrimitiveType(Class<?> type) {

    Class<?> result = type;
    if (type.isPrimitive()) {
      if (int.class == type) {
        return Integer.class;
      } else if (long.class == type) {
        return Long.class;
      } else if (double.class == type) {
        return Double.class;
      } else if (boolean.class == type) {
        return Boolean.class;
      } else if (float.class == type) {
        return Float.class;
      } else if (char.class == type) {
        return Character.class;
      } else if (byte.class == type) {
        return Byte.class;
      } else if (short.class == type) {
        return Short.class;
      } else if (void.class == type) {
        return Void.class;
      } else {
        throw new IllegalStateException("Class-loader isolation trap!");
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> GenericType<T> createGenericType(Class<T> type) {

    return new SimpleGenericTypeLimited<T>(type);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public GenericType<?> createGenericType(Type type) {

    NlsNullPointerException.checkNotNull(Type.class, type);
    if (type instanceof Class) {
      return new SimpleGenericTypeLimited((Class) type);
    } else if (type instanceof GenericType) {
      return (GenericType<?>) type;
    } else {
      throw new IllegalCaseException(type.toString());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> createGenericType(Type type, GenericType<?> definingType) {

    return createGenericType(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> createGenericType(Type type, Class<?> definingType) {

    return createGenericType(type);
  }

}
