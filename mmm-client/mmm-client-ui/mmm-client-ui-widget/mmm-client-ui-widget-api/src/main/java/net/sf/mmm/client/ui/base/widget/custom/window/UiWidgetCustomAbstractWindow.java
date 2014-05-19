/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetAbstractWindow;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomDynamicComposite;

/**
 * This is the abstract base class for a {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom custom
 * widget} implementing {@link UiWidgetAbstractWindow}.
 *
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomAbstractWindow<DELEGATE extends UiWidgetAbstractWindow> extends
    UiWidgetCustomDynamicComposite<Void, UiWidgetRegular, DELEGATE> implements UiWidgetAbstractWindow {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomAbstractWindow(UiContext context, DELEGATE delegate) {

    super(context, delegate, Void.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getDelegate().setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return getDelegate().getTitle();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(double x, double y) {

    getDelegate().setPosition(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionX() {

    return getDelegate().getPositionX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionY() {

    return getDelegate().getPositionY();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return getDelegate().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void centerWindow() {

    getDelegate().centerWindow();
  }

}
