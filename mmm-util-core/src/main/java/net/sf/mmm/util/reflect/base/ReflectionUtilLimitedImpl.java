/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
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
  @SuppressWarnings("unchecked")
  @Override
  public <T> Class<T> getNonPrimitiveType(Class<T> type) {

    Class<?> result = type;
    if (type.isPrimitive()) {
      if (int.class == type) {
        result = Integer.class;
      } else if (long.class == type) {
        result = Long.class;
      } else if (double.class == type) {
        result = Double.class;
      } else if (boolean.class == type) {
        result = Boolean.class;
      } else if (float.class == type) {
        result = Float.class;
      } else if (char.class == type) {
        result = Character.class;
      } else if (byte.class == type) {
        result = Byte.class;
      } else if (short.class == type) {
        result = Short.class;
      } else if (void.class == type) {
        result = Void.class;
      } else {
        throw new IllegalStateException("Class-loader isolation trap!");
      }
    }
    return (Class<T>) result;
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
