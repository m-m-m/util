/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import java.util.Date;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the abstract interface for a {@link UiWidgetTextualInputField input field widget} that represents a
 * date field. Such field allows to enter a value of the type {@link Date}. It should both support a textual
 * input field as well as a (icon-)button that opens a calendar popup to pick the date from. If this widget is
 * not available by the underlying native toolkit, an implementation shall be provided that is based on
 * {@link UiWidgetTextField}.
 *
 * <pre>
 * Appointment: <input type="date" />
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetDateField extends UiWidgetTextualInputField<Date>, UiWidgetNative {

  // nothing to add...

}
