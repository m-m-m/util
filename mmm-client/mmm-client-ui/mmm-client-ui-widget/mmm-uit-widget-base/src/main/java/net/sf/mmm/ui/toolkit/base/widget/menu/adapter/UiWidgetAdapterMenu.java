/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.menu.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterDynamicComposite;

/**
 * This is the interface for a {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapterMenu<WIDGET> extends UiWidgetAdapterDynamicComposite<WIDGET, UiWidgetMenuItem>,
    AttributeWriteLabel {

  // nothing to add

}
