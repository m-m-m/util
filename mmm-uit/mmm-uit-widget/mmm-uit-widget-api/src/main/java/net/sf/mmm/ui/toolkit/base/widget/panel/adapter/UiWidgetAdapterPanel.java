/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.panel.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterDynamicComposite;

/**
 * This is the interface for a {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract interface UiWidgetAdapterPanel<WIDGET> extends UiWidgetAdapterDynamicComposite<WIDGET, UiWidgetRegular> {

  // nothing to add

}
