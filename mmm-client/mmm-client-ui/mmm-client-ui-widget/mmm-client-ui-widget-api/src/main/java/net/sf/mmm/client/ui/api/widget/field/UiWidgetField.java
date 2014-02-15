/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTrimValue;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteValueAsString;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;

/**
 * This is the abstract interface for a {@link UiWidgetRegularComposite regular composite widget} that
 * represents a (potentially) editable field (text field, combobox, text area, radio-buttons, checkbox, etc.).
 * Such a field is associated with a {@link #getLabel() label}. To build a form with multiple fields a
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
    AttributeWriteValueAsString, AttributeWriteValidationFailure, AttributeWriteTrimValue, AttributeWriteLabel,
    UiWidgetActive {

  /** The default top-level {@link #hasStyle(String) style} of this widget. */
  String STYLE_FIELD = "Field";

  /**
   * The {@link #getPrimaryStyle() primary style} of the internal widget to display the {@link #getValue()
   * value} in {@link net.sf.mmm.client.ui.api.common.UiMode#VIEW view-mode}.
   */
  String STYLE_VIEW = "FieldView";

  /** The {@link #getPrimaryStyle() primary style} of the {@link #getLabelWidget() field label widget}. */
  String STYLE_LABEL = CssStyles.FIELD_LABEL;

  /**
   * The {@link #addStyle(String) additional style} of the {@link #getLabelWidget() field label widget} if
   * {@link #isMandatory() mandatory}.
   */
  String STYLE_MANDATORY = "Mandatory";

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <br/>
   * <b>NOTE:</b><br/>
   * Every field widget ({@link UiWidgetField}) has an associated {@link #getLabelWidget() label widget}. This
   * method gets its label text. Please note that the {@link #getLabelWidget() label widget} is not contained
   * in this field widget itself, so if you regularly add this field widget to some other widget you will not
   * see the label. However, {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel} also
   * automatically
   * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel#addChildren(net.sf.mmm.client.ui.api.widget.UiWidgetRegular...)
   * adds} the {@link #getLabelWidget() label widget}. The recommended use-case is to create a form to view
   * and edit your business object extending
   * {@link net.sf.mmm.client.ui.base.widget.custom.panel.UiWidgetCustomGridPanel} and use
   * {@link net.sf.mmm.client.ui.base.binding.UiDataBinding}.<br/>
   * Unlike <code>{@link #getLabelWidget()}.{@link UiWidgetLabel#getLabel() getLabel()}</code> this method
   * prevents actually creating the {@link #getLabelWidget() label widget} if it does NOT yet exist. Further
   * it will only return what has been set via {@link #setLabel(String)} and NOT what has potentially been set
   * via {@link #getLabelWidget()} . {@link UiWidgetLabel#setLabel(String) setLabel} (what should be avoided).
   */
  String getLabel();

  /**
   * {@inheritDoc}
   * 
   * @see #getLabel()
   */
  void setLabel(String label);

  /**
   * This method gets the {@link UiWidgetLabel label widget} associated with this field.<br/>
   * <b>ATTENTION:</b><br/>
   * Depending on the underlying toolkit the label widget is lazily created on the first call of this method.
   * Additionally users of this API (unlike implementors of the API) may only use this for very specific cases
   * (e.g. setting the style of the label). Therefore you should avoid calling this method unless you are
   * aware of what you are doing. To access the label-text simply use {@link #getLabel()} and
   * {@link #setLabel(String)}. Be aware that some native toolkits (e.g. SmartGWT) already implement field
   * widgets including the label. In this case this method will return a different view on the same native
   * widget.
   * 
   * @return the associated label widget.
   */
  UiWidgetLabel getLabelWidget();

}
