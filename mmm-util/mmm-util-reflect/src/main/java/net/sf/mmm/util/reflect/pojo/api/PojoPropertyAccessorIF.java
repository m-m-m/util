/* $Id$ */
package net.sf.mmm.util.reflect.pojo.api;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * A {@link PojoPropertyAccessorIF} gives {@link #getMethod() access} to a
 * specific {@link #getName() property} of a POJO.<br>
 * 
 * @see PojoPropertyDescriptorIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorIF {

  /**
   * This method gets {@link PojoPropertyDescriptorIF#getName() name} of the
   * according property.
   * 
   * @see PojoPropertyDescriptorIF#getName()
   * 
   * @return the property name.
   */
  String getName();

  /**
   * This method gets the {@link Method method} used to access (read/write) this
   * property.
   * 
   * @see java.beans.PropertyDescriptor#getReadMethod()
   * 
   * @return the method to read this property or <code>null</code> if this
   *         property is NOT readable.
   */
  Method getMethod();

  /**
   * This method gets the required type to {@link #getMethod() access}
   * (read/write) this property.
   * 
   * @see #getRawType()
   * @see java.beans.PropertyDescriptor#getPropertyType()
   * 
   * @return the required type to {@link #getMethod() access} this property or
   *         <code>null</code> if this property is NOT readable.
   */
  Type getGenericType();

  /**
   * This method gets the {@link #getGenericType() type} as raw class.
   * 
   * @return the raw type.
   */
  Class<?> getRawType();

  /**
   * This method gets the generic component type of a list property.
   * 
   * @see Class#getComponentType()
   * @see GenericArrayType#getGenericComponentType()
   * @see ParameterizedType#getActualTypeArguments()
   * 
   * @return the component type of this property or <code>null</code> if the
   *         {@link #getGenericType() type} is no {@link Class#isArray() array}
   *         or {@link Collection collection}.
   */
  Type getComponentType();

}
