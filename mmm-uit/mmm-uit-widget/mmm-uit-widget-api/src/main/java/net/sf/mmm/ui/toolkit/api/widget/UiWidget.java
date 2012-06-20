/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for an adapter to one or multiple physical widgets of the underlying native widget
 * toolkit (see <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of {@link #getValue()}.
 */
public abstract interface UiWidget<VALUE> extends AttributeWriteHtmlId, AttributeWriteVisible, AttributeWriteTooltip,
    AttributeWriteEnabled, AttributeWriteStyles, AttributeWriteValue<VALUE> {

  // nothing to add, just a combination of attribute/feature interfaces...

}
