/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValidation;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularComposite;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetWithValue;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;

/**
 * This is the abstract interface for a {@link UiWidgetRegularComposite regular composite widget} that
 * represents a editable field (text field, combobox, text area, radio-buttons, checkbox, etc.). It appears
 * confusing in the first place that this is a composite rather than an atomic widget, but a field widget may
 * also be composed out of multiple native input widgets (e.g. to edit a composite
 * {@link net.sf.mmm.util.lang.api.Datatype} - see
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#createForDatatype(Class)}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetField<VALUE> extends UiWidgetRegularComposite<UiWidget>, UiWidgetWithValue<VALUE>,
    UiFeatureValue<VALUE>, UiFeatureValidation<VALUE>, UiFeatureFocus, AttributeWriteLabel {

  /**
   * This method gets the {@link UiWidgetLabel label widget} associated with this field.<br/>
   * <b>ATTENTION:</b><br/>
   * Depending on the underlying toolkit the label widget is lazily created on the first call of this method.
   * Additionally users of this API (unlike implementors of the API) may only use this for very specific cases
   * (e.g. setting the style of the label). Therefore you should avoid calling this method unless you are
   * aware of what you are doing. To access the label-text simply use {@link #getLabel()} and
   * {@link #setLabel(String)}. Be aware that some native toolkits already implement field widgets including
   * the label. In this case this method will return a different view on the same native widget.
   * 
   * @return the associated label widget.
   */
  UiWidgetLabel getLabelWidget();

}
