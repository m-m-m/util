/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValidation;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularAtomic;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetWithValue;

/**
 * This is the abstract interface for a {@link UiWidgetRegularAtomic regular atomic widget} that represents a
 * editable field (text field, combobox, text area, radio-buttons, checkbox, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetField<VALUE> extends UiWidgetRegularAtomic, UiWidgetWithValue<VALUE>,
    UiFeatureValue<VALUE>, UiFeatureValidation<VALUE>, UiFeatureFocus, AttributeWriteValidationFailure,
    AttributeWriteLabel {

  // nothing to add

}
