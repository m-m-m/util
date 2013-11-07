/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import java.time.LocalTime;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField textual input field} that allows to display
 * and enter a value of the type {@link LocalTime} with hours, minutes and seconds. It should both support a
 * textual input field as well as a (icon-)button that opens a clock popup to pick the time from. If this
 * widget is not available by the underlying native toolkit, an implementation shall be provided that is based
 * on {@link UiWidgetTextField}.<br/>
 * Here you can see an example (with {@link #setLabel(String) field label} "Departure"):
 * 
 * <pre>
 * Departure: <input type="time" />
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetTimeField extends UiWidgetTextualInputField<LocalTime>, UiWidgetNative {

  // nothing to add...

}
