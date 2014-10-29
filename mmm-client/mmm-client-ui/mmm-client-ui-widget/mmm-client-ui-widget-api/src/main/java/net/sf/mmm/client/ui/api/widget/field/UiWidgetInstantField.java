/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import java.time.Instant;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that allows to display and
 * enter a value of the type {@link Instant} with day, month and year as well as the time in hours, minutes
 * and seconds. It should both support a textual input field as well as a (icon-)button that opens a calendar
 * popup to pick the date from. If this widget is not available by the underlying native toolkit, an
 * implementation shall be provided that is based on {@link UiWidgetTextField}. <br>
 * Here you can see an example (with {@link #setLabel(String) field label} "Appointment"):
 *
 * <pre>
 * Appointment: <input type="datetime" />
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetInstantField extends UiWidgetTextualInputField<Instant>, UiWidgetNative {

  // nothing to add...

}
