/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeType;
import net.sf.mmm.util.reflect.ReflectionException;

/**
 * This interface describes the {@link PojoPropertyDescriptor properties} of a
 * {@link net.sf.mmm.util.pojo.api.Pojo}. A
 * {@link net.sf.mmm.util.pojo.api.Pojo} in this manner is more or less
 * any java object.<br>
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
 * writing. Therefore the {@link java.beans.Introspector} for java-beans or <a
 * href="http://commons.apache.org/beanutils">commons-beanutils</a> can NOT be
 * used to read and write these properties. Using this utility the properties
 * can be accessed as described in the following table:<br>
 * <br>
 * <table border="1">
 * <tr>
 * <th>{@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() Name}</th>
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
 * </table><br>
 * <b>ATTENTION:</b><br>
 * When using this interface without generic parameterization you can NOT
 * properly call the
 * {@link #getAccessor(String, PojoPropertyAccessorMode, boolean) getAccessor}
 * methods. If the type of your {@link net.sf.mmm.util.pojo.api.Pojo} is
 * unknown at compile-time, you need to parameterize with the unbound wildcard
 * as <code>{@link PojoDescriptor}&lt;?&gt;</code>. In that case you can not
 * call the <code>get</code> or <code>set</code> methods.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoType() pojo}.
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
   *         property exists for the according {@link #getPojoType() pojo}.
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
   * @param property is the {@link PojoPropertyDescriptor#getName() name} of the
   *        property. If the given <code>mode</code> is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET GET}
   *        it is treated as for {@link #getProperty(Object, String)}. If the
   *        given <code>mode</code> is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET SET}
   *        it is treated as for {@link #setProperty(Object, String, Object)}.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @return the requested accessor or <code>null</code> if NOT found (there
   *         is no property named <code>propertyName</code>, the property has
   *         no accessor for the given <code>mode</code>, etc.).
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets the accessor for the given
   * <code>{@link PojoPropertyAccessor#getMode() mode}</code> from the
   * {@link #getPropertyDescriptor(String) descriptor} with the given
   * <code>{@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getName() propertyName}</code>.
   * 
   * @see #getPropertyDescriptor(String)
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param property is the {@link PojoPropertyDescriptor#getName() name} of the
   *        property. If the given <code>mode</code> is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET GET}
   *        it is treated as for {@link #getProperty(Object, String)}. If the
   *        given <code>mode</code> is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET SET}
   *        it is treated as for {@link #setProperty(Object, String, Object)}.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @param required - if <code>true</code> the accessor is required and an
   *        exception is thrown if NOT found.
   * @return the requested accessor or <code>null</code> if NOT found and
   *         <code>required</code> is <code>false</code>.
   * @throws PojoPropertyNotFoundException if <code>required</code> is
   *         <code>true</code> and no property named <code>propertyName</code>
   *         was found or no accessor exists for that property with the given
   *         <code>mode</code>.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required)
      throws PojoPropertyNotFoundException;

  /**
   * This method gets the {@link #getPropertyDescriptor(String) property}
   * identified by the given <code>property</code> from the given
   * <code>pojoInstance</code>. The result depends on the form of the given
   * <code>property</code> as shown by the following table:<br>
   * <table border="1">
   * <tr>
   * <th><code>property</code></th>
   * <th>accessor</th>
   * <th>example</th>
   * <th>equivalent</th>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]*</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET}</small></td>
   * <td>fooBar</td>
   * <td>
   * <ul>
   * <li>getFooBar()</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]* "[" [0-9]+ "]"</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED}</small></td>
   * <td>fooBar[42]</td>
   * <td>
   * <ul>
   * <li><code>getFooBar(42)</code></li>
   * <li><code>getFooBar()[42]</code></li>
   * <li><code>getFooBar().get(42)</code></li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]* "['" [a-zA-Z0-9]+ "']"</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#GET_MAPPED}</small></td>
   * <td>fooBar['key']</td>
   * <td>
   * <ul>
   * <li><code>getFooBar("key")</code></li>
   * <li><code>getFooBar().get("key")</code></li>
   * </ul>
   * </td>
   * </tr>
   * </table>
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param property identifies the property to get as described above.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getPropertyClass() type} of the
   *         according
   *         {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   *         Depending on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such
   *         {@link PojoPropertyAccessor accessor}.
   * @throws ReflectionException if the underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused
   *         an error during reflection.
   */
  Object getProperty(POJO pojoInstance, String property) throws PojoPropertyNotFoundException,
      ReflectionException;

  /**
   * This method sets the given <code>value</code> for the
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>property</code> of the given <code>pojoInstance</code>. The
   * effect depends on the form of the given <code>property</code> as shown by
   * the following table:<br>
   * <table border="1">
   * <tr>
   * <th><code>property</code></th>
   * <th>accessor</th>
   * <th>example</th>
   * <th>equivalent</th>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]*</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET}</small></td>
   * <td>fooBar</td>
   * <td>
   * <ul>
   * <li>setFooBar(value)</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]* "[" [0-9]+ "]"</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED}</small></td>
   * <td>fooBar[42]</td>
   * <td>
   * <ul>
   * <li><code>setFooBar(42, value)</code></li>
   * <li><code>getFooBar()[42]=value</code></li>
   * <li><code>getFooBar().set(42, value)</code></li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td><code>[a-zA-Z][a-zA-Z0-9]* "['" [a-zA-Z0-9]+ "']"</code></td>
   * <td><small>{@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode#SET_MAPPED}</small></td>
   * <td>fooBar['key']</td>
   * <td>
   * <ul>
   * <li><code>setFooBar("key", value)</code></li>
   * <li><code>getFooBar().put("key", value)</code></li>
   * </ul>
   * </td>
   * </tr>
   * </table>
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param property identifies the property to set as explained above.
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
  Object setProperty(POJO pojoInstance, String property, Object value)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET_SIZE size}
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
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#ADD add}
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
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#REMOVE remove}
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
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed getter}
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
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed setter}
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
