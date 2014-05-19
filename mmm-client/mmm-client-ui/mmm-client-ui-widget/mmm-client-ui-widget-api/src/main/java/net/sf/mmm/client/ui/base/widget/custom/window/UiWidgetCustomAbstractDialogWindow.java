/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClose;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventOpen;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetAbstractDialogWindow;

/**
 * This is the abstract base class for a {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom custom
 * widget} implementing {@link UiWidgetAbstractDialogWindow}.
 *
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomAbstractDialogWindow<DELEGATE extends UiWidgetAbstractDialogWindow> extends
    UiWidgetCustomAbstractWindow<DELEGATE> implements UiWidgetAbstractDialogWindow {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomAbstractDialogWindow(UiContext context, DELEGATE delegate) {

    super(context, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void open() {

    getDelegate().open();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {

    getDelegate().close();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addOpenHandler(UiHandlerEventOpen handler) {

    getDelegate().addOpenHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCloseHandler(UiHandlerEventClose handler) {

    getDelegate().addCloseHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeOpenHandler(UiHandlerEventOpen handler) {

    return getDelegate().removeOpenHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeCloseHandler(UiHandlerEventClose handler) {

    return getDelegate().removeCloseHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMovable(boolean movable) {

    getDelegate().setMovable(movable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximized() {

    return getDelegate().isMaximized();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximizable(boolean maximizable) {

    getDelegate().setMaximizable(maximizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximizable() {

    return getDelegate().isMaximizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    getDelegate().setResizable(resizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClosable(boolean closable) {

    getDelegate().setClosable(closable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    return getDelegate().isMovable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosable() {

    return getDelegate().isClosable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximized(boolean maximized) {

    getDelegate().setMaximized(maximized);
  }

}
