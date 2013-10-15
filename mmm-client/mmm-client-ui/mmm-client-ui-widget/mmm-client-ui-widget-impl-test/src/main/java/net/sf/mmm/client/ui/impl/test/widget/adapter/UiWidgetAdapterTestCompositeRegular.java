/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.SizeUnitHelper;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterSlot;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterTab;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterBorderPanel;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridCell;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterHorizontalPanel;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterVerticalPanel;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterMainWindow;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterPopup;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterComposite} and
 * various sub-interfaces bound to {@link UiWidgetRegular} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestCompositeRegular extends UiWidgetAdapterTestComposite<UiWidgetRegular> implements
    UiWidgetAdapterSlot, UiWidgetAdapterTab, UiWidgetAdapterHorizontalPanel, UiWidgetAdapterVerticalPanel,
    UiWidgetAdapterBorderPanel, UiWidgetAdapterGridCell, UiWidgetAdapterPopup, UiWidgetAdapterMainWindow {

  /** @see #getPositionX() */
  private double positionX;

  /** @see #getPositionY() */
  private double positionY;

  /** @see #isMaximized() */
  private boolean maximized;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestCompositeRegular() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumnSpan(int columnSpan) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    verifyNotDisposed();
    return SizeUnitHelper.convertToPixel(getWidth(), 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    verifyNotDisposed();
    return SizeUnitHelper.convertToPixel(getHeight(), 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMovable(boolean movable) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosable() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClosable(boolean closable) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximizable() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximizable(boolean maximizable) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximized() {

    verifyNotDisposed();
    return this.maximized;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximized(boolean maximized) {

    verifyNotDisposed();
    // change size?
    this.maximized = maximized;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(double x, double y) {

    verifyNotDisposed();
    this.positionX = x;
    this.positionY = y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionX() {

    verifyNotDisposed();
    return this.positionX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionY() {

    verifyNotDisposed();
    return this.positionY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMenuBar(UiWidgetMenuBar menuBar) {

    verifyNotDisposed();
  }

}
