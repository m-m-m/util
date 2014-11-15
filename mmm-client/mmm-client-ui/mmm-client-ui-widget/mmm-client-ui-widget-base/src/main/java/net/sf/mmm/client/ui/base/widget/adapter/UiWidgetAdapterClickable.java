/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;

/**
 * This is the interface for {@link UiWidgetAdapterActive} that has a {@link #setLabel(String) label}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterClickable extends UiWidgetAdapterActive, UiWidgetAdapterWithLabel,
    AttributeWriteImage<UiWidgetImage> {

  // nothing to add...

}
