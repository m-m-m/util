/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This interface gives advanced read access to the {@link #getValue() value} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface AttributeReadValueAdvanced<VALUE> extends AttributeReadValue<VALUE> {

  /**
   * {@inheritDoc}
   * 
   * This will typically be a newly created object. <br>
   * <b>ATTENTION:</b><br>
   * If the current value entered by the user can NOT be parsed, this method will catch and ignore the
   * exception and return <code>null</code> instead. If you want to do validation and give feedback to the
   * user please use {@link #getValueOrException(Object)} instead. However, there are higher-level ways to do
   * this such as
   * {@link net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation#getValueAndValidate(net.sf.mmm.util.validation.api.ValidationState)
   * getValueAndValidate}.
   */
  @Override
  VALUE getValue();

  /**
   * This method is like {@link #getValue()} but does NOT catch exceptions while parsing the value from the
   * user input. Additionally it allows to provide a template object that gets populated. This allows advanced
   * features with polymorphism as you can also provide a sub-class of &lt;VALUE&gt;.
   * 
   * @param template is the object where the data is filled in. May also be <code>null</code> - then this
   *        method will create a new instance.
   * @return the current value of this widget. May be <code>null</code> if empty. If the value type is
   *         {@link String} the empty {@link String} has to be returned if no value has been entered. In case
   *         &lt;VALUE&gt; is a mutable object (java bean) and <code>template</code> is NOT <code>null</code>,
   *         this method is supposed to return <code>template</code>.
   * @throws RuntimeException if the entered value is invalid (e.g. paring caused a
   *         {@link NumberFormatException}).
   */
  VALUE getValueOrException(VALUE template) throws RuntimeException;

  /**
   * This method gets the last value that has been {@link AttributeWriteValueAdvanced#setValue(Object) set}.
   * After calling {@link AttributeWriteValueAdvanced#setValue(Object)} the value can be modified (by the user
   * in case of a widget). Then calls to {@link #getValue()} will return the current value with its potential
   * modifications (as a new object). This method will ensure to get the value that was
   * {@link AttributeWriteValueAdvanced#setValue(Object) set} before by the program. <br>
   * <b>ATTENTION:</b><br>
   * The original value shall never be modified to avoid undesired side effects. Various features such as
   * {@link AttributeWriteValueAdvanced#resetValue()} rely on this fact. Therefore {@link #getValue()} will
   * always return a new instance.
   * 
   * @return the original value.
   */
  VALUE getOriginalValue();

}
