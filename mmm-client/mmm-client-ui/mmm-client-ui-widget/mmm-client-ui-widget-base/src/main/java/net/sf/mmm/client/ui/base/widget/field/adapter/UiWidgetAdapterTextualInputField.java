/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximumTextLength;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetTextualInputField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 */
public interface UiWidgetAdapterTextualInputField<VALUE, ADAPTER_VALUE> extends
    UiWidgetAdapterField<VALUE, ADAPTER_VALUE>, AttributeWriteMaximumTextLength {

  // nothing to add

}
