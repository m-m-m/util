/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the interface for an adapter that keeps reflective operations out of the implementation of
 * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding}.<br/>
 * <b>ATTENTION:</b><br/>
 * This is just a simple abstraction of reflective introspection solutions such as
 * {@link net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder}. However, we want to be
 * the minimum needed so users can replace reflective approaches with generated code or the like. Please note
 * that there is no reflection available in GWT context and therefore you will have limitations. If your
 * custom needs differ from what is offered out of the box, this is the place where to hook into.
 * 
 * @param <VALUE> is the generic type of the value to adapt. Typically a
 *        {@link net.sf.mmm.util.transferobject.api.TransferObject} or a
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDataBindingAdapter<VALUE> {

  /**
   * @return a new instance of {@literal <VALUE>}.
   */
  VALUE createNewValue();

  /**
   * This method creates a copy (e.g. via copy-constructor or {@link Cloneable clone}) of the given
   * <code>value</code>. If the given <code>value</code> is immutable (like {@link String}, {@link Long},
   * etc.) it is legal to return the same given instance.
   * 
   * @param value is the value to copy.
   * @return the copy of the value.
   */
  VALUE copy(VALUE value);

  /**
   * This method gets or creates the {@link ValueValidator} for the specified property.
   * 
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getValidator()
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() property type}.
   * 
   * @param property is the {@link TypedProperty}.
   * @return the {@link ValueValidator} for the given property.
   */
  <T> ValueValidator<T> getPropertyValidator(TypedProperty<T> property);

  /**
   * This method gets the {@link TypedProperty#getPropertyType() property type} of the given
   * {@link TypedProperty}. This method might work even if {@link TypedProperty#getPropertyType()} returns
   * <code>null</code>.
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() property type}.
   * 
   * @param property is the {@link TypedProperty}.
   * @return the {@link TypedProperty#getPropertyType() property type} of the given <code>property</code>.
   */
  <T> Class<T> getPropertyType(TypedProperty<T> property);

  /**
   * This method gets the value of the specified property.<br/>
   * E.g. <code>getPropertyValue(pojo, "foo")</code> is supposed to return <code>pojo.getFoo()</code>.
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() property type}.
   * 
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param property is the {@link TypedProperty}.
   * @return the value of the specified property.
   */
  <T> T getPropertyValue(VALUE pojo, TypedProperty<T> property);

  /**
   * This method sets the value of the specified property.<br/>
   * E.g. <code>setPropertyValue(pojo, "foo", value)</code> is supposed to do <code>pojo.setFoo(value)</code>.
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() property type}.
   * 
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} owning the property.
   * @param property is the {@link TypedProperty}.
   * @param propertyValue is the property value to set.
   */
  <T> void setPropertyValue(VALUE pojo, TypedProperty<T> property, T propertyValue);

}
