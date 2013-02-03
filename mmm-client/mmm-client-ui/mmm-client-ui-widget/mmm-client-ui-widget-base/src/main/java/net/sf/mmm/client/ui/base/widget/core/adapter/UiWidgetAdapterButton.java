/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteImage;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActiveWithLabel;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterWithLabel;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterButton extends UiWidgetAdapterActiveWithLabel, UiWidgetAdapterWithLabel,
    AttributeWriteImage<UiWidgetImage> {

  // nothing to add

}
