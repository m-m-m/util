/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.window;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterMainWindow;

/**
 * This is the abstract base implementation of {@link UiWidgetMainWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMainWindow<ADAPTER extends UiWidgetAdapterMainWindow<?>> extends
    AbstractUiWidgetBaseWindow<ADAPTER> implements UiWidgetMainWindow {

  /** @see #getMenuBar() */
  private UiWidgetMenuBar menuBar;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetMainWindow(AbstractUiWidgetFactory<?> factory) {

    super(factory);
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
      this.menuBar = getFactory().create(UiWidgetMenuBar.class);
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setMenuBar(this.menuBar);
      }
    }
    return this.menuBar;
  }

}
