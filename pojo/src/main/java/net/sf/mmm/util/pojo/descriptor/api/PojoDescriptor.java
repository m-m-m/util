/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeType;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This interface describes the {@link PojoPropertyDescriptor properties} of a {@link net.sf.mmm.util.pojo.api.Pojo}. A
 * {@link net.sf.mmm.util.pojo.api.Pojo} in this manner is more or less any java object. <br>
 * This interface is an alternative to {@link java.beans.BeanInfo}. <br>
 * Look at the following example:
 *
 * <pre>
 * public interface Pojo {
 *   Integer getFooBar();
 *
 *   void setFooBar(int s);
 *
 *   boolean hasSomeFlag();
 *
 *   void setSomeFlag(Boolean flag);
 *
 *   boolean isCool();
 *
 *   void setCool();
 *
 *   {@literal List<String> getColors();}
 *
 *   void addColor(String color);
 *
 *   void removeColor(String color);
 * }
 * </pre>
 *
 * This interface does NOT completely follow the JAVA-Beans specification. The properties "fooBar" and "someFlag" do NOT
 * have the same type for reading and writing. Therefore the {@link java.beans.Introspector} for java-beans or
 * <a href="http://commons.apache.org/beanutils">commons-beanutils</a> can NOT be used to read and write these
 * properties. Using this utility the properties can be accessed as described in the following table:<br>
 * <br>
 * <table border="1">
 * <tr>
 * <th>{@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() Name}</th>
 * <th>{@link PojoPropertyAccessorMode Mode}</th>
 * <th>{@link PojoAttributeType#getPojoClass() Property-Type}</th>
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
 * <td>{@code List<String>}</td>
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
 * <br>
 * <b>ATTENTION:</b><br>
 * When using this interface without generic parameterization you can NOT properly call the
 * {@link #getAccessor(String, PojoPropertyAccessorMode, boolean) getAccessor} methods. If the type of your
 * {@link net.sf.mmm.util.pojo.api.Pojo POJO} is unknown at compile-time, you need to parameterize with the unbound
 * wildcard as {@link PojoDescriptor PojoDescriptor<?>}{@code <&>}. In that case you can not call the {@code get} or
 * {@code set} methods.
 *
 * @param <POJO> is the templated type of the {@link #getPojoClass() pojo}.
 *
 * @see PojoPropertyDescriptor
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoDescriptor<POJO> extends PojoAttributeType<POJO> {

  /**
   * Creates a new instance. Only possible if a (public) default constructor is available.
   *
   * @return a {@link Class#newInstance() new instance} of the {@link net.sf.mmm.util.pojo.api.Pojo POJO}.
   * @since 3.1.0
   */
  POJO newInstance();

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor} for the property identified by the given
   * {@code propertyName}.
   *
   * @param propertyName is the name of the requested property.
   * @return the descriptor for the property identified by the given {@code propertyName} or {@code null} if no such
   *         property exists for the according {@link #getPojoClass() pojo}.
   */
  PojoPropertyDescriptor getPropertyDescriptor(String propertyName);

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor} for the given {@code property}.
   *
   * @param property is the {@link TypedProperty}.
   * @return the descriptor for the property identified by the given {@code property}.
   * @throws PojoPropertyNotFoundException if the specified {@code property} was NOT found.
   * @since 3.1.0
   */
  PojoPropertyDescriptor getPropertyDescriptor(TypedProperty<?> property) throws PojoPropertyNotFoundException;

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor}s of all properties of the according
   * {@link #getPojoClass() pojo}.
   *
   * @return a collection with all {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} for the property with the given {@code propertyName} and
   * for the given access {@code mode}.
   *
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param property is the {@link PojoPropertyDescriptor#getName() name} of the property. If the given {@code mode} is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET GET} it is treated as
   *        for {@link #getProperty(Object, String)}. If the given {@code mode} is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET SET} it is treated as
   *        for {@link #setProperty(Object, String, Object)}.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the requested accessor. Use
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorModes} for available modes.
   * @return the requested accessor or {@code null} if NOT found (there is no property named {@code propertyName}, the
   *         property has no accessor for the given {@code mode}, etc.).
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets the accessor for the given {@link PojoPropertyAccessor#getMode() mode} from the
   * {@link #getPropertyDescriptor(String) descriptor} with the given
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getName() propertyName} .
   *
   * @see #getPropertyDescriptor(String)
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
   *
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param property is the {@link PojoPropertyDescriptor#getName() name} of the property. If the given {@code mode} is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET GET} it is treated as
   *        for {@link #getProperty(Object, String)}. If the given {@code mode} is
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET SET} it is treated as
   *        for {@link #setProperty(Object, String, Object)}.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the requested accessor. Use
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorModes} for available modes.
   * @param required - if {@code true} the accessor is required and an exception is thrown if NOT found.
   * @return the requested accessor or {@code null} if NOT found and {@code required} is {@code false}.
   * @throws PojoPropertyNotFoundException if {@code required} is {@code true} and no property named
   *         {@code propertyName} was found or no accessor exists for that property with the given {@code mode}.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required) throws PojoPropertyNotFoundException;

  /**
   * This method gets the {@link #getPropertyDescriptor(String) property} identified by the given {@code property} from
   * the given {@code pojo}. The result depends on the form of the given {@code property} as shown by the following
   * table:<br>
   * <table border="1">
   * <tr>
   * <th>{@code property}</th>
   * <th>accessor</th>
   * <th>example</th>
   * <th>equivalent</th>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]*}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET} </small></td>
   * <td>fooBar</td>
   * <td>
   * <ul>
   * <li>getFooBar()</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]* "[" [0-9]+ "]"}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED}
   * </small></td>
   * <td>fooBar[42]</td>
   * <td>
   * <ul>
   * <li>{@code getFooBar(42)}</li>
   * <li>{@code getFooBar()[42]}</li>
   * <li>{@code getFooBar().get(42)}</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]* "['" [a-zA-Z0-9]+ "']"}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#GET_MAPPED} </small>
   * </td>
   * <td>fooBar['key']</td>
   * <td>
   * <ul>
   * <li>{@code getFooBar("key")}</li>
   * <li>{@code getFooBar().get("key")}</li>
   * </ul>
   * </td>
   * </tr>
   * </table>
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param property identifies the property to get as described above.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getPropertyClass() type} of the according
   *         {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}. Depending
   *         on the POJO, the value may be {@code null}.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} (getter).
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Object getProperty(POJO pojo, String property) throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the value of the specified property in a type-safe way.
   *
   * @see #getProperty(Object, String)
   *
   * @param <V> is the generic type of the requested property value.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param property is the {@link TypedProperty} identifying the property to get.
   * @return the value of the specified property.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} (getter).
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   * @since 3.1.0
   */
  <V> V getProperty(POJO pojo, TypedProperty<V> property)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method sets the given {@code value} for the {@link #getPropertyDescriptor(String) property} with the given
   * {@code property} of the given {@code pojo}. The effect depends on the form of the given {@code property} as shown
   * by the following table:<br>
   * <table border="1">
   * <tr>
   * <th>{@code property}</th>
   * <th>accessor</th>
   * <th>example</th>
   * <th>equivalent</th>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]*}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#SET} </small></td>
   * <td>fooBar</td>
   * <td>
   * <ul>
   * <li>setFooBar(value)</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]* "[" [0-9]+ "]"}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED}
   * </small></td>
   * <td>fooBar[42]</td>
   * <td>
   * <ul>
   * <li>{@code setFooBar(42, value)}</li>
   * <li>{@code getFooBar()[42]=value}</li>
   * <li>{@code getFooBar().set(42, value)}</li>
   * </ul>
   * </td>
   * </tr>
   * <tr>
   * <td>{@code [a-zA-Z][a-zA-Z0-9]* "['" [a-zA-Z0-9]+ "']"}</td>
   * <td><small> {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode#SET_MAPPED} </small>
   * </td>
   * <td>fooBar['key']</td>
   * <td>
   * <ul>
   * <li>{@code setFooBar("key", value)}</li>
   * <li>{@code getFooBar().put("key", value)}</li>
   * </ul>
   * </td>
   * </tr>
   * </table>
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param property identifies the property to set as explained above.
   * @param value is the property value to set. Depending on the POJO the value may be {@code null}.
   * @return the result of the setter method. Will be {@code null} if the return type is {@code void} what should be the
   *         regular case.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} (setter).
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Object setProperty(POJO pojo, String property, Object value)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method sets the value of the specified property in a type-safe way.
   *
   * @see #setProperty(Object, String, Object)
   *
   * @param <V> is the generic type of the property value to set.
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param property is the {@link TypedProperty} identifying the property to set.
   * @param value is the new property value to set.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} (setter).
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   * @since 3.1.0
   */
  <V> void setProperty(POJO pojo, TypedProperty<V> property, V value)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET_SIZE
   * size} of the {@link #getPropertyDescriptor(String) property} with the given {@code propertyName} from the given
   * {@code pojo}.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of the property.
   * @return the size of the requested property.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} .
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  int getPropertySize(POJO pojo, String propertyName) throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method adds the given {@code item} to the list-like {@link #getPropertyDescriptor(String) property} with the
   * given {@code propertyName} from the given {@code pojo} using the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#ADD add}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of the property.
   * @param item is the item to add to the property.
   * @return the result of the add-method. Will be {@code null} if the return type is {@code void} what should be the
   *         regular case.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} .
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Object addPropertyItem(POJO pojo, String propertyName, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method removes the given {@code item} from an array or {@link Collection} using the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#REMOVE remove}
   *
   * {@link #getPropertyDescriptor(String) property} with the given {@code propertyName} from the given {@code pojo}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of the property.
   * @param item is the item to remove from the property.
   * @return {@link Boolean#TRUE} if the item has been removed successfully and {@link Boolean#FALSE} if the item was
   *         NOT present in the array or {@link Collection}, or {@code null} if the accessor is pointing to a remove
   *         method that returns no boolean.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} .
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Boolean removePropertyItem(POJO pojo, String propertyName, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method gets the item with the given {@code index} from the list-like {@link #getPropertyDescriptor(String)
   * property} with the given {@code propertyName} of the given {@code pojo} using the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed
   * getter} {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to add the given property {@code item}.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of the property.
   * @param index is the position of the requested item (see {@link java.util.List#get(int)}).
   * @return the result of the add-method. Will be {@code null} if the return type is {@code void} what should be the
   *         regular case.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} .
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Object getPropertyItem(POJO pojo, String propertyName, int index)
      throws PojoPropertyNotFoundException, ReflectionException;

  /**
   * This method sets the given {@code item} at the given {@code index} in the list-like
   * {@link #getPropertyDescriptor(String) property} with the given {@code propertyName} of the given {@code pojo} using
   * the {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed
   * setter} {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode) accessor}.
   *
   * @param pojo is the {@link #getPojoClass() POJO} instance where to access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of the property.
   * @param index is the position of the item to set (see {@link java.util.List#set(int, Object)}).
   * @param item is the item to set at the given {@code index}.
   * @return the result of the add-method. Will be {@code null} if the return type is {@code void} what should be the
   *         regular case.
   * @throws PojoPropertyNotFoundException if the property with the given {@code propertyName} was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no such {@link PojoPropertyAccessor accessor} .
   * @throws ReflectionException if the underlying {@link PojoPropertyAccessor#getAccessibleObject() accessor} caused an
   *         error during reflection.
   */
  Object setPropertyItem(POJO pojo, String propertyName, int index, Object item)
      throws PojoPropertyNotFoundException, ReflectionException;

}
