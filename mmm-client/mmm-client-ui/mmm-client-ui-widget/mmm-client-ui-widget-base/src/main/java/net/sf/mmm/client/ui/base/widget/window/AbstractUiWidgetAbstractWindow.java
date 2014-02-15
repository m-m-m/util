/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetAbstractWindow;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractWindow;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractWindow}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractWindow<ADAPTER extends UiWidgetAdapterAbstractWindow> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetRegular> implements UiWidgetAbstractWindow {

  /** @see #getTitle() */
  private String title;

  /** @see #getPositionX() */
  private double x;

  /** @see #getPositionY() */
  private double y;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractWindow(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
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
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void centerWindow() {

    getWidgetAdapter();
    AttributeReadSizeInPixel desktop = getScreenBase();

    double desktopWidth = desktop.getWidthInPixel();
    double windowWidth = getWidthInPixel();
    double xDiff = desktopWidth - windowWidth;
    double desktopHeight = desktop.getHeightInPixel();
    double windowHeight = getHeightInPixel();
    double yDiff = desktopHeight - windowHeight;
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
  public void setLength(LengthProperty property, Length value) {

    LengthUnit unit = value.getUnit();
    Length newValue;
    switch (unit) {
      case PIXEL:
        newValue = value;
        break;
      case PERCENT:
        double screenLength;
        if (property.isWidth()) {
          screenLength = getScreenBase().getWidthInPixel();
        } else {
          screenLength = getScreenBase().getHeightInPixel();
        }
        double amount = screenLength * value.getAmount() / 100.0;
        newValue = Length.valueOfPixel(amount);
        break;
      case EM:
        newValue = convertEmToPixel(value);
        break;
      default :
        throw new IllegalCaseException(LengthUnit.class, unit);
    }
    super.setLength(property, newValue);
  }

  /**
   * Converts a {@link Length} given in {@link LengthUnit#EM} to {@link LengthUnit#PIXEL}.
   * 
   * @param length is the {@link Length} to convert.
   * @return the converted {@link Length}.
   */
  private Length convertEmToPixel(Length length) {

    assert (length.getUnit() == LengthUnit.EM);
    return Length.valueOfPixel(length.getAmount() * 16.0);
  }

  /**
   * @return the instance of {@link AttributeReadSizeInPixel} used to determine the base of the screen to
   *         center or convert {@link Length}.
   */
  private AttributeReadSizeInPixel getScreenBase() {

    UiWidgetMainWindow mainWindow = getContext().getWidgetFactory().getMainWindow();
    AttributeReadSizeInPixel desktop = mainWindow;
    if ((mainWindow == this) || mainWindow.isWindowPositionAbsolute()) {
      desktop = getContext().getDisplay();
    }
    return desktop;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible, boolean programmatic) {

    super.setVisible(visible, programmatic);
    // ensure that the widget adapter has been created at this time...
    getWidgetAdapter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    if (this.title != null) {
      return this.title;
    }
    return super.getSource();
  }

}
