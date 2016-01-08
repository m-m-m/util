/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.util.List;

import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is a simple abstraction of reflective introspection solutions such as
 * {@link net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder} or even
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator}. However, within some limitations (a particular base-class or
 * marker interface is required) this is GWT compatible and therefore abstracts intorspection to a minimum. Please note
 * that there is no reflection available in GWT context and therefore you will have limitations. If your custom needs
 * differ from what is offered out of the box, this is the place where to hook into.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 * @deprecated this was only planned but never meant to be released. As there was never an implementation of this
 *             interface and it was never used anywhere in the project it is marked as deprecated and will be removed in
 *             the next release.
 */
@Deprecated
public interface PojoUtilLimited {

  /**
   * This method creates a copy (e.g. via copy-constructor or {@link Cloneable clone}) of the given <code>value</code>.
   * If the given <code>value</code> is immutable (like {@link String}, {@link Long}, etc.) it is legal to return the
   * same given instance.
   *
   * @param <V> is the generic type of the value to copy. Typically a
   *        {@link net.sf.mmm.util.transferobject.api.TransferObject} or a {@link net.sf.mmm.util.lang.api.Datatype}.
   * @param value is the value to copy.
   * @return the copy of the value.
   */
  <V> V copy(V value);

  /**
   * This method determines if the given object is a (immutable) {@link net.sf.mmm.util.lang.api.Datatype}. It will
   * return <code>true</code> if the given <code>value</code> is a {@link String}, {@link Boolean}, {@link Character},
   * anything derived from {@link Number}, an {@link Enum}, an instance of {@link net.sf.mmm.util.lang.api.Datatype}, a
   * {@link java.util.Date} (even though not immutable) or anything similar should be considered as datatype due to a
   * custom extension.
   *
   * @param value is the object to check.
   * @return <code>true</code> if the given value is a (immutable) {@link net.sf.mmm.util.lang.api.Datatype},
   *         <code>false</code> otherwise.
   */
  boolean isDatatype(Object value);

  /**
   * This method gets an {@link java.util.Collections#unmodifiableList(List) unmodifiable} {@link List} with the
   * {@link TypedProperty properties} of the given {@link net.sf.mmm.util.pojo.api.Pojo} type.
   *
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor#getPropertyDescriptors()
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory
   *
   * @param pojoType is the class reflecting the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @return a {@link List} with the {@link TypedProperty properties} of the given <code>pojoType</code>.
   */
  List<TypedProperty<?>> getProperties(Class<?> pojoType);

  /**
   * This method gets or creates the {@link ValueValidator} for the specified property derived from annotations (e.g.
   * JSR 303 or JSR 308). <br>
   * <b>ATTENTION:</b><br>
   * This is NOT an implementation of JSR 303 (<code>javax.validation</code>) nor is it strictly compatible to it.
   * Instead this is an abstraction that may be implemented using JSR 303 or maybe not. However, we want to be GWT
   * compatible and we want to directly bind the validation to the UI widgets (see
   * <code>UiWidgetWithValue#addValidator(ValueValidator)</code>). The API of JSR 303 is unfortunately biased. A simple
   * bridge to JSR 303 can be achieved via
   * <code>javax.validation.Validator.validateProperty(Object, String, Class)</code>.
   *
   * @param pojoType is the class reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param propertyName is the {@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() name}
   *        of the property.
   * @return the {@link ValueValidator} for the given property.
   */
  ValueValidator<?> getPropertyValidator(Class<?> pojoType, String propertyName);

  /**
   * This method gets the value of the specified property. <br>
   * E.g. <code>getPropertyValue(pojo, "foo")</code> should return <code>pojo.getFoo()</code>. <br>
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param propertyName is the {@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() name}
   *        of the property.
   * @return the value of the specified property.
   */
  Object getPropertyValue(Object pojo, String propertyName);

  /**
   * This method gets the value of the specified property.
   *
   * @see #getPropertyValue(Object, String)
   *
   * @param <V> is the generic type of the requested property value.
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param property is the {@link TypedProperty} identifying the property to get.
   * @return the value of the specified property.
   */
  <V> V getPropertyValue(Object pojo, TypedProperty<V> property);

  /**
   * This method sets the value of the specified property. <br>
   * E.g. <code>setPropertyValue(pojo, "foo", value)</code> should have the same effect as
   * <code>pojo.setFoo(value)</code>. <br>
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param propertyName is the {@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() name}
   *        of the property.
   * @param value is the new property value to set.
   */
  void setPropertyValue(Object pojo, String propertyName, Object value);

  /**
   * This method sets the value of the specified property.
   *
   * @see #setPropertyValue(Object, String, Object)
   *
   * @param <V> is the generic type of the property value to set.
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param property is the {@link TypedProperty} identifying the property to set.
   * @param value is the new property value to set.
   */
  <V> void setPropertyValue(Object pojo, TypedProperty<V> property, V value);

}
