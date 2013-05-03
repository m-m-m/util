/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteAccessKey;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget} with {@link #setFocused()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterActive extends UiWidgetAdapter, AttributeWriteFocused, AttributeWriteAccessKey {

  // nothing to add...
}
