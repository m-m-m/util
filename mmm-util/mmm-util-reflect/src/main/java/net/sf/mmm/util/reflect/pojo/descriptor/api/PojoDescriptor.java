/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.api;

import java.util.Collection;

import net.sf.mmm.util.reflect.ReflectionException;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.attribute.PojoAttributeType;

/**
 * This interface describes the {@link PojoPropertyDescriptor properties} of a
 * {@link net.sf.mmm.util.reflect.pojo.Pojo}. A
 * {@link net.sf.mmm.util.reflect.pojo.Pojo} in this manner is more or less any
 * java object.<br>
 * This interface is an alternative to {@link java.beans.BeanInfo}.<br>
 * Look at the following example:
 * 
 * <pre>
 * public interface Pojo {
 *   Integer getFooBar();
 *   void setFooBar(int s);
 *   
 *   boolean hasSomeFlag();
 *   void setSomeFlag(Boolean flag);
 *   
 *   boolean isCool();
 *   void setCool();
 *   
 *   List&lt;String&gt; getColors();
 *   void addColor(String color);
 *   void removeColor(String color);
 * }
 * </pre>
 * 
 * This interface does NOT completely follow the JAVA-Beans specification. The
 * properties "fooBar" and "someFlag" do NOT have the same type for reading and
 * writing. Therefore {@link java.beans} or
 * <code>org.apache.commons.beanutils</code> can NOT be used to read and write
 * these properties. Using this utility the properties can be accessed as
 * described in the following table:<br>
 * <br>
 * <table border="1">
 * <tr>
 * <th>{@link net.sf.mmm.util.reflect.pojo.descriptor.api.attribute.PojoAttributeName#getName() Name}</th>
 * <th>{@link PojoPropertyAccessorMode Mode}</th>
 * <th>{@link PojoAttributeType#getPojoType() Property-Type}</th>
 * <th>{@link PojoPropertyAccessor#getAccessibleObject() Method}</th>
 * <th>Note</th>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>get</td>
 * <td>Integer</td>
 * <td>getFooBar()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>fooBar</td>
 * <td>set</td>
 * <td>int</td>
 * <td>setFooBar(int)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>get</td>
 * <td>boolean</td>
 * <td>hasSomeFlag()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>someFlag</td>
 * <td>set</td>
 * <td>Boolean</td>
 * <td>setSomeFlag(Boolean)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>cool</td>
 * <td>get</td>
 * <td>boolean</td>
 * <td>isCool()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>colors</td>
 * <td>get</td>
 * <td>List&lt;String&gt;</td>
 * <td>getColors()</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>color</td>
 * <td>add</td>
 * <td>String</td>
 * <td>addColor(String)</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>color</td>
 * <td>remove</td>
 * <td>String</td>
 * <td>removeColor(String)</td>
 * <td>-</td>
 * <tr>
 * <td>colors</td>
 * <td>add</td>
 * <td>String</td>
 * <td>addColor(String)</td>
 * <td>enhanced copy</td>
 * </tr>
 * <tr>
 * <td>colors</td>
 * <td>remove</td>
 * <td>String</td>
 * <td>removeColor(String)</td>
 * <td>enhanced copy</td>
 * </tr>
 * <tr>
 * <td>colors</td>
 * <td>indexed-set</td>
 * <td>String</td>
 * <td>getColors().set(int, String)</td>
 * <td>enhanced virtual accessor</td>
 * </tr>
 * </table>
 * 
 * @param <POJO> is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @see PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptor<POJO> extends PojoAttributeType<POJO> {

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor} for the
   * property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName is the name of the requested property.
   * @return the descriptor for the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no such
   *         property exists for the according {@link #getPojoType() POJO}.
   */
  PojoPropertyDescriptor getPropertyDescriptor(String propertyName);

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} for the property
   * with the given <code>propertyName</code> and for the given access
   * <code>mode</code>.
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @return the requested accessor or <code>null</code> if NOT found (there
   *         is no property named <code>propertyName</code>, the property has
   *         no accessor for the given <code>mode</code>, etc.).
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets the {@link #getPropertyDescriptor(String) property} with
   * the given <code>propertyName</code> from the given
   * <code>pojoInstance</code> using the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getPropertyClass() type} of the
   *         according
   *         {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   *         Depending on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object getProperty(POJO pojoInstance, String propertyName) throws PojoPropertyNotFoundException,
      ReflectionException;

  /**
   * This method sets the given <code>value</code> for the
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET setter}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param value is the property value to set. Depending on the POJO the value
   *        may be <code>null</code>.
   * @return the result of the setter method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object setProperty(POJO pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#SIZE size}
   * of the {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @return the size of the requested property.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  int getPropertySize(POJO pojoInstance, String propertyName) throws PojoPropertyNotFoundException,
      ReflectionException;

  /**
   * This method adds the given <code>item</code> to the list-like
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#ADD add}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param item is the item to add to the property.
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object addPropertyItem(POJO pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method removes the given <code>item</code> from an array or
   * {@link Collection} using the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#REMOVE remove}
   * 
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param item is the item to remove from the property.
   * @return {@link Boolean#TRUE} if the item has been removed successfully and
   *         {@link Boolean#FALSE} if the item was NOT present in the array or
   *         {@link Collection}, or <code>null</code> if the accessor is
   *         pointing to a remove method that returns no boolean.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Boolean removePropertyItem(POJO pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the item with the given <code>index</code> from the
   * list-like {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> of the given <code>pojoInstance</code> using
   * the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed getter}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        add the given property <code>item</code>.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param index is the position of the requested item (see
   *        {@link java.util.List#get(int)}).
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object getPropertyItem(POJO pojoInstance, String propertyName, int index)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method sets the given <code>item</code> at the given
   * <code>index</code> in the list-like
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> of the given <code>pojoInstance</code> using
   * the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed setter}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param index is the position of the item to set (see
   *        {@link java.util.List#set(int, Object)}).
   * @param item is the item to set at the given <code>index</code>.
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object setPropertyItem(POJO pojoInstance, String propertyName, int index, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

}
