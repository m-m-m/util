/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOnlyStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOnlyVisible;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for an adapter to one or multiple physical widgets of the underlying native widget
 * toolkit (see <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>).
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiAdapter<VALUE> extends AttributeWriteHtmlId, AttributeWriteOnlyVisible,
    AttributeWriteTooltip, AttributeWriteEnabled, AttributeWriteOnlyStyles, AttributeWriteValue<VALUE> {

  // nothing to add, just a combination of attribute/feature interfaces...

}
