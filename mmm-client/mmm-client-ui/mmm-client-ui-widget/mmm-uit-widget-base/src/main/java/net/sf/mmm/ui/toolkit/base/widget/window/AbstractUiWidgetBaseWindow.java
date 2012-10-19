/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteResizable;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetBaseWindow;
import net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.window.adapter.UiWidgetAdapterBaseWindow;

/**
 * This is the abstract base implementation of {@link UiWidgetBaseWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetBaseWindow<ADAPTER extends UiWidgetAdapterBaseWindow<?>> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetRegular> implements UiWidgetBaseWindow, AttributeWriteResizable {

  /** @see #getTitle() */
  private String title;

  /** @see #getPositionX() */
  private int x;

  /** @see #getPositionY() */
  private int y;

  /** @see #getWidthInPixel() */
  private int width;

  /** @see #getHeightInPixel() */
  private int height;

  /** @see #isResizable() */
  private boolean resizable;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetBaseWindow(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.resizable = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.title != null) {
      adapter.setTitle(this.title);
    }
    if ((this.x != 0) || (this.y != 0)) {
      adapter.setPosition(this.x, this.y);
    }
    if (this.width != 0) {
      adapter.setWidthInPixel(this.width);
    }
    if (this.height != 0) {
      adapter.setHeightInPixel(this.width);
    }
    adapter.setResizable(this.resizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int widthInPixel) {

    this.width = widthInPixel;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setWidthInPixel(widthInPixel);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int heightInPixel) {

    this.height = heightInPixel;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setHeightInPixel(heightInPixel);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int widthInPixel, int heightInPixel) {

    this.width = widthInPixel;
    this.height = heightInPixel;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSizeInPixel(widthInPixel, heightInPixel);
    }
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

    this.resizable = resizable;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setResizable(resizable);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.title = title;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitle(title);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(int xPos, int yPos) {

    this.x = xPos;
    this.y = yPos;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setPosition(xPos, yPos);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionX() {

    if (hasWidgetAdapter()) {
      int positionX = getWidgetAdapter().getPositionX();
      if (positionX != Integer.MIN_VALUE) {
        return positionX;
      }
    }
    return this.x;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    if (hasWidgetAdapter()) {
      int positionY = getWidgetAdapter().getPositionY();
      if (positionY != Integer.MIN_VALUE) {
        return positionY;
      }
    }
    return this.y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    if (isResizable() && hasWidgetAdapter()) {
      return getWidgetAdapter().getWidthInPixel();
    }
    return this.width;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    if (isResizable() && hasWidgetAdapter()) {
      return getWidgetAdapter().getHeightInPixel();
    }
    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void centerWindow() {

    UiWidgetMainWindow mainWindow = getFactory().getMainWindow();
    AttributeReadSizeInPixel desktop = mainWindow;
    if (!mainWindow.isWindowPositionAbsolute()) {
      desktop = getFactory().getDisplay();
    }

    int xDiff = desktop.getWidthInPixel() - getWidthInPixel();
    int yDiff = desktop.getHeightInPixel() - getHeightInPixel();
    if (xDiff < 0) {
      xDiff = 0;
    }
    if (yDiff < 0) {
      yDiff = 0;
    }
    setPosition(xDiff / 2, yDiff / 2);
  }

}
