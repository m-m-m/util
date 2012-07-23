/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextFieldBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 */
public interface UiWidgetAdapterTextFieldBase<WIDGET, VALUE> extends UiWidgetAdapterInputField<WIDGET, VALUE, String>,
    AttributeWriteKeyboardFilter {

  // nothing to add

}
