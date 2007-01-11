/* $Id$ */
package net.sf.mmm.util.pojo.impl;

import java.beans.ConstructorProperties;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;

/**
 * This is the implementation of the {@link PojoPropertyAccessor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorImpl implements PojoPropertyAccessor {

  /** @see #getName() */
  private final String name;

  /** @see #getMethod() */
  private final Method method;

  /** @see #getGenericType() */
  private final Type type;

  /** @see #getRawType() */
  private final Class<?> rawType;

  /** @see #getComponentType() */
  private final Type componentType;

  /**
   * The constructor.
   * 
   * @param propertyName
   *        is the {@link #getName() name} of the property.
   * @param propertyMethod
   *        is the {@link #getMethod() method} to access the property.
   * @param propertyType
   *        is the type reflecting the property.
   * @param propertyClass
   */
  @ConstructorProperties( {"name", "method", "type", "rawType"})
  public PojoPropertyAccessorImpl(String propertyName, Method propertyMethod, Type propertyType,
      Class<?> propertyClass) {

    super();
    this.name = propertyName;
    this.method = propertyMethod;
    this.rawType = propertyClass;
    this.type = propertyType;
    Type cmpType = null;
    if (this.type instanceof GenericArrayType) {
      cmpType = ((GenericArrayType) this.type).getGenericComponentType();
    } else if (Collection.class.isAssignableFrom(this.rawType)) {
      if (this.type instanceof ParameterizedType) {
        Type[] genericTypes = ((ParameterizedType) this.type).getActualTypeArguments();
        if (genericTypes.length == 1) {
          cmpType = genericTypes[0];
        } else {
          // TODO
          throw new IllegalArgumentException();
        }
      } else {
        cmpType = Object.class;
      }
    } else if (this.rawType.isArray()) {
      cmpType = this.rawType.getComponentType();
    }
    this.componentType = cmpType;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getName()
   */
  public String getName() {

    return this.name;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getMethod()
   */
  public Method getMethod() {

    return this.method;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getGenericType()
   */
  public Type getGenericType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getRawType()
   */
  public Class<?> getRawType() {

    return this.rawType;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getComponentType()
   */
  public Type getComponentType() {

    return this.componentType;
  }

}
