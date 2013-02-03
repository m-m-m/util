/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterMainWindow;

/**
 * This is the abstract base implementation of {@link UiWidgetMainWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMainWindow<ADAPTER extends UiWidgetAdapterMainWindow> extends
    AbstractUiWidgetBaseWindow<ADAPTER> implements UiWidgetMainWindow {

  /** @see #getMenuBar() */
  private UiWidgetMenuBar menuBar;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetMainWindow(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.menuBar != null) {
      adapter.setMenuBar(this.menuBar);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMenuBar getMenuBar() {

    if (this.menuBar == null) {
      this.menuBar = getContext().getWidgetFactory().create(UiWidgetMenuBar.class);
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setMenuBar(this.menuBar);
      }
    }
    return this.menuBar;
  }

}
