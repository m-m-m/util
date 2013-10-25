/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetOptionsBase;

/**
 * This is the abstract interface for a {@link UiWidgetField field widget} that allows to select a single value out of a
 * list of predefined options.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetOptionsField<VALUE> extends UiWidgetField<VALUE>, UiWidgetOptionsBase<VALUE> {

  // nothing to add...

}
