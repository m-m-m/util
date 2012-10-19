/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.menu;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for a {@link UiWidgetMenuItem menu item widget} that has a {@link #getLabel() label}
 * and additionally a {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetCheckbox checkbox}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetCheckbox
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenuItemCheckbox extends UiWidgetMenuItem, AttributeWriteLabel, AttributeWriteValue<Boolean>,
    UiWidgetReal {

  // nothing to add

}
