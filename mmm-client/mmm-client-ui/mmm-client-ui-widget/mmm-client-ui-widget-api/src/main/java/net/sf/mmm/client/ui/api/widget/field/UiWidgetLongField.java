/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a {@link Long}
 * field. Such field allows to enter a value of the type {@link Long}. For advanced usability the widget shall be a so
 * called <em>spin box</em> that has small arrows to increase and decrease the current value. However, this widget
 * exists in order to expose native support for this feature (that might have advanced features or better performance).
 * If this is not available by the underlying native toolkit, an implementation shall be provided that is based on
 * {@link UiWidgetTextField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetLongField extends UiWidgetTextualInputField<Long>, UiWidgetRangeField<Long>,
    UiWidgetNative {

  // nothing to add...

}
