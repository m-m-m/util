/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;

/**
 * This is the abstract interface for a {@link UiWidgetAtomicRegular regular atomic widget} that represents a
 * editable field. Such widget always has a {@link #getLabel() label} that represents a {@link UiWidgetLabel}
 * but is combined with an additional widget that allows to display or edit data.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetEditor<VALUE> extends UiWidgetAtomicRegular, AttributeWriteLabel,
    UiFeatureValue<VALUE> {

  // nothing to add...

}
