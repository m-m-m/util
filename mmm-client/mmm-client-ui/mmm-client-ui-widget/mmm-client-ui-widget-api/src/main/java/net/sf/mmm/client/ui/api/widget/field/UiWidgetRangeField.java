/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteMinimumValue;

/**
 * This is the abstract interface for a {@link UiWidgetField field widget} that represents a range field. Such
 * field allows to enter a value that has to be in a range from {@link #getMinimumValue() minimum} to
 * {@link #getMaximumValue() maximum}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetRangeField<VALUE> extends UiWidgetField<VALUE>, AttributeWriteMinimumValue<VALUE>,
    AttributeWriteMaximumValue<VALUE> {

  // nothing to add...

}
