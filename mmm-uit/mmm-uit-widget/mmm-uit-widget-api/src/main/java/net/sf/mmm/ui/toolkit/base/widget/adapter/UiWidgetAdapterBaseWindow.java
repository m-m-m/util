/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWritePosition;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteResizable;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSizeInPixel;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteTitle;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetBaseWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapterBaseWindow<WIDGET> extends UiWidgetAdapterSingleComposite<WIDGET, UiWidgetRegular>,
    AttributeWriteSizeInPixel, AttributeWritePosition, AttributeWriteTitle<String>, AttributeWriteResizable {

  // nothing to add

}
