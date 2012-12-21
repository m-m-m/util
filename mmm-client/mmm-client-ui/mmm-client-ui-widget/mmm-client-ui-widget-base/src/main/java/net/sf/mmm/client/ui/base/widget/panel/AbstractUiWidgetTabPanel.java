/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetTab;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetSwitchComposite;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterTabPanel;

/**
 * This is the abstract base implementation of {@link UiWidgetTabPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetTabPanel<ADAPTER extends UiWidgetAdapterTabPanel<?>> extends
    AbstractUiWidgetSwitchComposite<ADAPTER, UiWidgetTab> implements UiWidgetTabPanel {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetTabPanel(AbstractUiContext context) {

    super(context);
  }

}
