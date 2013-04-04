/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.core.adapter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterImage;
import net.sf.mmm.client.ui.impl.javafx.widget.adapter.UiWidgetAdapterJavaFxNode;

/**
 * This is the implementation of {@link UiWidgetAdapterImage} using GWT based on {@link ImageView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterJavaFxImage extends UiWidgetAdapterJavaFxNode<ImageView> implements UiWidgetAdapterImage {

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFxImage() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ImageView createToplevelWidget() {

    return new ImageView();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAltText(String altText) {

    // How to do this in the API?
    // getToplevelWidget().setAltText(altText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    getToplevelWidget().setImage(new Image(url));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    getActiveWidget().setScaleX(convertLength(width));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    getActiveWidget().setScaleY(convertLength(height));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    return Length.valueOfPixel(getActiveWidget().getScaleX());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    return Length.valueOfPixel(getActiveWidget().getScaleY());
  }

}
