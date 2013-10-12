/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteTrimValue;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;

/**
 * This is the abstract interface for a {@link UiWidgetRegularComposite regular composite widget} that
 * represents a (potentially) editable field (text field, combobox, text area, radio-buttons, checkbox, etc.).
 * Such a field is associated with a {@link #getFieldLabel() label}. To build a form with multiple fields a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel} should be used (typically indirectly via a
 * custom widget). Such form can be used to display the fields with their values in
 * {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view mode} (read-only) or in
 * {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit mode}. This can be changed dynamically via
 * {@link #setMode(net.sf.mmm.client.ui.api.common.UiMode)}. To prevent a single field from switching to
 * {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit mode} you can call
 * {@link #setModeFixed(net.sf.mmm.client.ui.api.common.UiMode)} with
 * {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW} so it will remain immutable. Besides a field can also
 * be {@link #isEnabled() disabled} so in {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit mode} it
 * will be grayed out and prevents the user from changing its value. This is suggested for fields that
 * dynamically change their ( {@link #isEnabled() editable}) status based on a particular condition (e.g. the
 * current value of another field). A field widget is a composite because it may also be composed out of
 * multiple native input widgets (e.g. to edit a composite {@link net.sf.mmm.util.lang.api.Datatype} - see
 * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype#createForDatatype(Class)}).
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetField<VALUE> extends UiWidgetRegularComposite<UiWidget>, UiWidgetWithValue<VALUE>,
    AttributeWriteValidationFailure, AttributeWriteTrimValue, UiWidgetActive {

  /** The default top-level {@link #hasStyle(String) style} of this widget. */
  String STYLE_FIELD = "Field";

  /**
   * The {@link #getPrimaryStyle() primary style} of the internal widget to display the {@link #getValue()
   * value} in {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}.
   */
  String STYLE_VIEW = "FieldView";

  /** The {@link #getPrimaryStyle() primary style} of the {@link #getFieldLabelWidget() field label widget}. */
  String STYLE_LABEL = "FieldLabel";

  /**
   * The {@link #addStyle(String) additional style} of the {@link #getFieldLabelWidget() field label widget}
   * if {@link #isMandatory() mandatory}.
   */
  String STYLE_MANDATORY = "Mandatory";

  /**
   * This method is like
   * <code>{@link #getFieldLabelWidget()}.{@link UiWidgetLabel#getLabel() getLabel()}</code> without actually
   * creating the {@link #getFieldLabelWidget() field label widget} if it does NOT yet exist. Further it will
   * only return what has been set via {@link #setFieldLabel(String)} and NOT what has potentially been set
   * via {@link #getFieldLabelWidget()}.{@link UiWidgetLabel#setLabel(String) setLabel} (what should be
   * avoided).
   * 
   * @return the {@link UiWidgetLabel#getLabel() label} of the {@link #getFieldLabelWidget() field label
   *         widget} widget} or <code>null</code> if NOT set.
   */
  String getFieldLabel();

  /**
   * This method is logically equivalent to
   * <code>{@link #getFieldLabelWidget()}.{@link UiWidgetLabel#setLabel(String) setLabel(String)}</code>
   * without actually creating the {@link #getFieldLabelWidget() field label widget} if it does NOT yet exist.
   * In the latter case the given label is stored and will automatically be assigned if
   * {@link #getFieldLabelWidget()} will create the label widget.
   * 
   * @param label is the new {@link UiWidgetLabel#getLabel() label} for the {@link #getFieldLabelWidget()
   *        field label widget} widget}.
   */
  void setFieldLabel(String label);

  /**
   * This method gets the {@link UiWidgetLabel label widget} associated with this field.<br/>
   * <b>ATTENTION:</b><br/>
   * Depending on the underlying toolkit the label widget is lazily created on the first call of this method.
   * Additionally users of this API (unlike implementors of the API) may only use this for very specific cases
   * (e.g. setting the style of the label). Therefore you should avoid calling this method unless you are
   * aware of what you are doing. To access the label-text simply use {@link #getFieldLabel()} and
   * {@link #setFieldLabel(String)}. Be aware that some native toolkits (e.g. SmartGWT) already implement
   * field widgets including the label. In this case this method will return a different view on the same
   * native widget.
   * 
   * @return the associated label widget.
   */
  UiWidgetLabel getFieldLabelWidget();

}
