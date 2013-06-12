/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api;

import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;

/**
 * This is the interface for the configuration of the UI (user interface).<br/>
 * <b>ATTENTION:</b><br/>
 * This is an {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extendable interface}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiConfiguration {

  /** The default value for {@link #getTheme() theme}. */
  String DEFAULT_THEME = "standard";

  /**
   * This method gets the path for the current theme. This is attached to all relative image URLs. You can use
   * this to switch to a different theme in order to get a different appearance of the UI. Typically you
   * decide for a theme at build-time. However, this allows to switch themes at runtime.
   * 
   * @return the URL path for the current theme.
   */
  String getTheme();

  /**
   * This method builds the label from the given parameters. It may append a static suffix like colon (":").
   * Further it may append a static suffix like asterisk ("*") if the given {@link UiWidgetWithValue widget}
   * is {@link UiWidgetWithValue#isMandatory() mandatory}. Further its decision may change if the
   * {@link UiWidgetWithValue widget} is in {@link net.sf.mmm.client.ui.api.common.UiMode#isEditable()
   * editable} {@link UiWidgetWithValue#getMode() mode}.
   * 
   * @param label is the original label of the widget as
   *        {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel#setLabel(String) set} by the user.
   * @param widget is the widget owning the label, typically a
   *        {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField field}.
   * @return the final label to display in the UI.
   */
  String buildLabel(String label, UiWidgetWithValue<?> widget);

  // focus on set edit mode: first editable field, off, ...
  // focus on validation failure: first invalid field, off, ...
  // validation failure handling: popup, message panel, popup & message panel, ...
  // tab control: tab passive fields, only active fields (accessibility setting?)

}
