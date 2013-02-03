/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetDynamicPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterDynamicComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetDynamicPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDynamicPanel<ADAPTER extends UiWidgetAdapterDynamicComposite<CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetDynamicComposite<ADAPTER, CHILD> implements UiWidgetDynamicPanel<CHILD> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDynamicPanel(AbstractUiContext context) {

    super(context);
  }

}
