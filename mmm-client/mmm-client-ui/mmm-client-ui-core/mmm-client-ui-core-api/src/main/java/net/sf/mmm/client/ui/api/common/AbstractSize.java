/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This is the abstract base implementation of the {@link Size} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSize implements Size {

  /**
   * The constructor.
   */
  public AbstractSize() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    return getLength(LengthProperty.WIDTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    setLength(LengthProperty.WIDTH, width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(double widthInPixel) {

    setWidth(Length.valueOfPixel(widthInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPercent(double widthInPercent) {

    setWidth(Length.valueOfPercent(widthInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    return getLength(LengthProperty.HEIGHT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    setLength(LengthProperty.HEIGHT, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPercent(double heightInPercent) {

    setHeight(Length.valueOfPercent(heightInPercent));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(double heightInPixel) {

    setHeight(Length.valueOfPixel(heightInPixel));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getMinimumWidth() {

    return getLength(LengthProperty.MIN_WIDTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumWidth(Length minWidth) {

    setLength(LengthProperty.MIN_WIDTH, minWidth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getMinimumHeight() {

    return getLength(LengthProperty.MIN_HEIGHT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumHeight(Length minHeight) {

    setLength(LengthProperty.MIN_HEIGHT, minHeight);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getMaximumWidth() {

    return getLength(LengthProperty.MAX_WIDTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumWidth(Length maxWidth) {

    setLength(LengthProperty.MAX_WIDTH, maxWidth);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getMaximumHeight() {

    return getLength(LengthProperty.MAX_HEIGHT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumHeight(Length maxHeight) {

    setLength(LengthProperty.MAX_HEIGHT, maxHeight);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(double width, double height, LengthUnit unit) {

    setSize(unit.newLength(width), unit.newLength(height));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(Length width, Length height) {

    // might be overridden in a more efficient way...
    setWidth(width);
    setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumSize(Length minWidth, Length minHeight) {

    setMinimumWidth(minWidth);
    setMinimumHeight(minHeight);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumSize(Length maxWidth, Length maxHeight) {

    setMaximumWidth(maxWidth);
    setMaximumHeight(maxHeight);
  }

}
