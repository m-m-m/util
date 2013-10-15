/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetAbstractDialogWindow;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractDialogWindow;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractDialogWindow}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractDialogWindow<ADAPTER extends UiWidgetAdapterAbstractDialogWindow> extends
    AbstractUiWidgetAbstractWindow<ADAPTER> implements UiWidgetAbstractDialogWindow {

  /** @see #isResizable() */
  private boolean resizable;

  /** @see #isMovable() */
  private boolean movable;

  /** @see #isClosable */
  private boolean closable;

  /** @see #isMaximizable() */
  private boolean maximizable;

  /** @see #isMaximized() */
  private boolean maximized;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetAbstractDialogWindow(UiContext context) {

    super(context);
    setPrimaryStyle(STYLE_PRIMARY);
    this.movable = true;
    this.resizable = true;
    this.closable = true;
    this.maximized = false;
    this.maximizable = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    adapter.setResizable(this.resizable);
    adapter.setMovable(this.movable);
    adapter.setClosable(this.closable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return this.resizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    if (this.resizable == resizable) {
      return;
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setResizable(resizable);
    }
    this.resizable = resizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    return this.movable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMovable(boolean movable) {

    if (this.movable == movable) {
      return;
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMovable(movable);
    }
    this.movable = movable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosable() {

    return this.closable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClosable(boolean closable) {

    if (this.closable == closable) {
      return;
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setClosable(closable);
    }
    this.closable = closable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximizable() {

    return this.maximizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximizable(boolean maximizable) {

    if (this.maximizable != maximizable) {
      return;
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximizable(maximizable);
    }
    this.maximizable = maximizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximized() {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().isMaximized();
    }
    return this.maximized;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximized(boolean maximized) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximized(maximized);
    }
    this.maximized = maximized;
  }

}
