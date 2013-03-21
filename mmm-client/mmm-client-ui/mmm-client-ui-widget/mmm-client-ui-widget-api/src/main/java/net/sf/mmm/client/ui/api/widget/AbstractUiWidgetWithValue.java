/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteValueAdvanced;

/**
 * This is the interface for a {@link UiWidget} that has a {@link #getValue() value}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractUiWidgetWithValue<VALUE> extends UiWidget, AttributeWriteValueAdvanced<VALUE> {

  // nothing to add...

}
