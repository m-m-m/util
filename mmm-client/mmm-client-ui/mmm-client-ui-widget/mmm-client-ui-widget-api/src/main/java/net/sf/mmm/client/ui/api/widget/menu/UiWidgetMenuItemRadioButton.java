/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.menu;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for a {@link UiWidgetMenuItem menu item widget} that has a {@link #getLabel() label}
 * and additionally a radio button.<br/>
 * All {@link UiWidgetMenuItemRadioButton radio menu items} within the same {@link UiWidgetMenu menu} will
 * automatically belong to the same group (so only one of them can be selected at a time).
 * 
 * @see net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtons
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenuItemRadioButton extends UiWidgetMenuItem, AttributeWriteLabel,
    AttributeWriteValue<Boolean>, UiWidgetReal {

  // nothing to add

}
