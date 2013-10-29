/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.client.ui.api.common.Color;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetField field widget} that allows to display and enter a value of the type
 * {@link Color}. Besides the {@link Color#getTitle() textual representation} of the {@link Color} it should also
 * display the {@link Color#getValue() value} is a visual representation as a little box filled with the current
 * {@link Color}, called <em>color box</em>. The color box should always be visible (in any
 * {@link net.sf.mmm.client.ui.api.common.UiMode}). Further, clicking on the color box in
 * {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit mode} should open a popup with an advanced color picker.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetColorField extends UiWidgetField<Color>, AttributeWriteOptions<Color>, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "ColorField";

}
