/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the interface for an adapter that keeps reflective operations out of the implementation of
 * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding}. It may be implemented via reflection, be completely
 * generated, or via {@link net.sf.mmm.util.transferobject.api.TransferObjectUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDataBindingAdapter {

  /**
   * This method creates a copy (e.g. via copy-constructor or {@link Cloneable clone}) of the given
   * <code>value</code>. If the given <code>value</code> is immutable (like {@link String}, {@link Long},
   * etc.) it is legal to return the same given instance.
   * 
   * @param <V> is the generic type of the value to copy. Typically a
   *        {@link net.sf.mmm.util.transferobject.api.TransferObject} or a
   *        {@link net.sf.mmm.util.lang.api.Datatype}.
   * @param value is the value to copy.
   * @return the copy of the value.
   */
  <V> V copy(V value);

  /**
   * This method determines if the given object is a (immutable) {@link net.sf.mmm.util.lang.api.Datatype}. It
   * will return <code>true</code> if the given <code>value</code> is a {@link String}, {@link Boolean},
   * {@link Character}, anything derived from {@link Number}, an {@link Enum}, an instance of
   * {@link net.sf.mmm.util.lang.api.Datatype}, a {@link java.util.Date} (even though not immutable) or
   * anything similar should be considered as datatype due to a custom extension.
   * 
   * @param value is the object to check.
   * @return <code>true</code> if the given value is a (immutable) {@link net.sf.mmm.util.lang.api.Datatype},
   *         <code>false</code> otherwise.
   */
  boolean isDatatype(Object value);

  /**
   * This method gets or creates the {@link ValueValidator} for the specified property derived from
   * annotations (e.g. JSR 303 or JSR 308).<br/>
   * <b>ATTENTION:</b><br/>
   * This is NOT an implementation of JSR 303 (<code>javax.validation</code>) nor is it strictly compatible to
   * it. Instead this is an abstraction that may be implemented using JSR 303 or maybe not. However, we want
   * to be GWT compatible and we want to
   * {@link net.sf.mmm.client.ui.api.widget.UiWidgetWithValue#addValidator(ValueValidator) directly bind the
   * validation} to the {@link net.sf.mmm.client.ui.api.widget.UiWidget}. The API of JSR 303 is unfortunately
   * biased. A simple bridge to JSR 303 can be achieved via
   * <code>javax.validation.Validator.validateProperty(Object, String, Class)</code>.
   * 
   * @param pojoType is the class reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param propertyName is the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() name} of the
   *        property.
   * @return the {@link ValueValidator} for the given property.
   */
  ValueValidator<?> getValidatorForProperty(Class<?> pojoType, String propertyName);

  /**
   * This method gets the value of the specified property.<br/>
   * E.g. <code>getPropertyValue(pojo, "foo")</code> should return <code>pojo.getFoo()</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * This is just a simple abstraction of reflective introspection solutions such as
   * {@link net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder} or even
   * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator}. However, we want to be GWT compatible and
   * therefore abstract this to the minimum needed. Please note that there is no reflection available in GWT
   * context and therefore you will have limitations. If your custom needs differ from what is offered out of
   * the box, this is the place where to hook into.
   * 
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param propertyName is the
   *        {@link net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName#getName() name} of the
   *        property.
   * @return the value of the specified property.
   */
  Object getPropertyValue(Object pojo, String propertyName);

}
