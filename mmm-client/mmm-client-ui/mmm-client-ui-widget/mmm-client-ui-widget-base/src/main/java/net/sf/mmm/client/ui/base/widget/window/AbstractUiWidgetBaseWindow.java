/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window;

import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetBaseWindow;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterBaseWindow;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is the abstract base implementation of {@link UiWidgetBaseWindow}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetBaseWindow<ADAPTER extends UiWidgetAdapterBaseWindow> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetRegular> implements UiWidgetBaseWindow, AttributeWriteResizable {

  /** @see #getTitle() */
  private String title;

  /** @see #getPositionX() */
  private double x;

  /** @see #getPositionY() */
  private double y;

  /** @see #isResizable() */
  private boolean resizable;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetBaseWindow(AbstractUiContext context) {

    super(context);
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
    adapter.setResizable(this.resizable);
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
  public void setPosition(double xPos, double yPos) {

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
  public double getPositionX() {

    if (hasWidgetAdapter()) {
      double positionX = getWidgetAdapter().getPositionX();
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
  public double getPositionY() {

    if (hasWidgetAdapter()) {
      double positionY = getWidgetAdapter().getPositionY();
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
  public double getWidthInPixel() {

    if (isResizable() && hasWidgetAdapter()) {
      return getWidgetAdapter().getWidthInPixel();
    }
    Length width = getWidth();
    if (width == null) {
      return 0;
    }
    assert (width.getUnit() == SizeUnit.PIXEL);
    return width.getAmount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    if (isResizable() && hasWidgetAdapter()) {
      return getWidgetAdapter().getHeightInPixel();
    }
    Length height = getHeight();
    if (height == null) {
      return 0;
    }
    assert (height.getUnit() == SizeUnit.PIXEL);
    return height.getAmount();
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

    AttributeReadSizeInPixel desktop = getScreenBase();

    double xDiff = desktop.getWidthInPixel() - getWidthInPixel();
    double yDiff = desktop.getHeightInPixel() - getHeightInPixel();
    if (xDiff < 0) {
      xDiff = 0;
    }
    if (yDiff < 0) {
      yDiff = 0;
    }
    setPosition(xDiff / 2, yDiff / 2);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Length convertHeight(Length newHeight) {

    SizeUnit unit = newHeight.getUnit();
    switch (unit) {
      case PIXEL:
        return super.convertHeight(newHeight);
      case PERCENT:
        double amount = getScreenBase().getHeightInPixel() * newHeight.getAmount() / 100.0;
        return Length.valueOfPixel(amount);
      case EM:
        return convertEmToPixel(newHeight);
      default :
        throw new IllegalCaseException(SizeUnit.class, unit);
    }

  }

  /**
   * Converts a {@link Length} given in {@link SizeUnit#EM} to {@link SizeUnit#PIXEL}.
   * 
   * @param length is the {@link Length} to convert.
   * @return the converted {@link Length}.
   */
  private Length convertEmToPixel(Length length) {

    assert (length.getUnit() == SizeUnit.EM);
    return Length.valueOfPixel(length.getAmount() * 16.0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Length convertWidth(Length newWidth) {

    SizeUnit unit = newWidth.getUnit();
    switch (unit) {
      case PIXEL:
        return super.convertWidth(newWidth);
      case PERCENT:
        double amount = getScreenBase().getWidthInPixel() * newWidth.getAmount() / 100.0;
        return Length.valueOfPixel(amount);
      case EM:
        return convertEmToPixel(newWidth);
      default :
        throw new IllegalCaseException(SizeUnit.class, unit);
    }
  }

  /**
   * @return the instance of {@link AttributeReadSizeInPixel} used to determine the base of the screen to
   *         center or convert {@link Length}.
   */
  private AttributeReadSizeInPixel getScreenBase() {

    UiWidgetMainWindow mainWindow = getContext().getWidgetFactory().getMainWindow();
    AttributeReadSizeInPixel desktop = mainWindow;
    if ((mainWindow == this) || !mainWindow.isWindowPositionAbsolute()) {
      desktop = getContext().getDisplay();
    }
    return desktop;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    // ensure that the widget adapter has been created at this time...
    getWidgetAdapter();
    super.setVisible(visible);
  }

}
