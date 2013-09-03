/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;

/**
 * This is the interface for an {@link UiWidgetWithValue regular widget} that represents a
 * <em>toggle button</em>. It is a {@link #getValue() stateful} {@link UiWidgetButton button} that toggles
 * between <em>up</em> and <em>down</em> state with each click. So it is some sort of hybrid mixture of a
 * {@link UiWidgetButton button} and a {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField
 * checkbox}. Such widget commonly has an {@link #setImage(UiWidgetImage) icon} and a
 * {@link #setTooltip(String) tooltip}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetToggleButton extends UiWidgetWithValue<Boolean>, UiWidgetAbstractButton, UiWidgetNative {

  /** The {@link #addStyle(String) additional style} of this widget. */
  String STYLE_TOGGLE_BUTTON = "ToggleButton";

  /** The {@link #addStyle(String) additional style} in case this toggle button is pressed down. */
  String STYLE_PRESSED = "Pressed";

}
