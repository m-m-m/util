/* $Id$ */
package net.sf.mmm.util.pojo.base;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;

/**
 * This is the implementation of the {@link PojoPropertyAccessor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessor implements PojoPropertyAccessor {

  /** @see #getName() */
  private final String name;

  /** @see #getAccessMode() */
  private final PojoPropertyAccessMode mode;

  /** @see #getPropertyType() */
  private final Type type;

  /** @see #getPropertyClass() */
  private final Class<?> clazz;

  /** @see #getPropertyComponentType() */
  private final Type componentType;

  /**
   * The constructor.
   * 
   * @param propertyName
   *        is the {@link #getName() name} of the property.
   * @param accessMode
   *        is the {@link #getAccessMode() mode} of this accessor.
   * @param propertyType
   *        is the {@link #getPropertyType() generic type} of the property.
   * @param propertyClass
   *        is the {@link #getPropertyClass() raw type} of the property.
   */
  public AbstractPojoPropertyAccessor(String propertyName, PojoPropertyAccessMode accessMode,
      Type propertyType, Class<?> propertyClass) {

    super();
    this.name = propertyName;
    this.mode = accessMode;
    this.clazz = propertyClass;
    this.type = propertyType;
    // determine component type...
    Type cmpType = null;
    if (this.type instanceof GenericArrayType) {
      cmpType = ((GenericArrayType) this.type).getGenericComponentType();
    } else if (Collection.class.isAssignableFrom(this.clazz)) {
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
    } else if (this.clazz.isArray()) {
      cmpType = this.clazz.getComponentType();
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
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getAccessMode()
   */
  public PojoPropertyAccessMode getAccessMode() {

    return this.mode;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getPropertyType()
   */
  public Type getPropertyType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getPropertyClass()
   */
  public Class<?> getPropertyClass() {

    return this.clazz;
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getPropertyComponentType()
   */
  public Type getPropertyComponentType() {

    return this.componentType;
  }

  /**
   * This method invokes this accessor as getter.
   * 
   * @param pojoInstance
   *        the instance of the pojo where to get the property value from.
   * @return the value of the property.
   * @throws IllegalAccessException
   *         if you do NOT have permissions the access the underlying getter
   *         method.
   * @throws InvocationTargetException
   *         if the POJO itself (the getter) throws an exception.
   */
  public abstract Object get(Object pojoInstance) throws IllegalAccessException,
      InvocationTargetException;

  /**
   * 
   * @param pojoInstance
   * @param value
   * @throws IllegalAccessException
   *         if you do NOT have permissions the access the underlying getter
   *         method.
   * @throws InvocationTargetException
   *         if the POJO itself (the getter) throws an exception.
   */
  public abstract void set(Object pojoInstance, Object value) throws IllegalAccessException,
      InvocationTargetException;

}
