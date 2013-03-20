/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.core.adapter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.mmm.client.ui.api.common.SizeUnit;
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
  public void setWidth(String width) {

    getActiveWidget().setScaleX(convertSizeUnit(width));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(String height) {

    getActiveWidget().setScaleY(convertSizeUnit(height));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(String width, String height) {

    setWidth(width);
    setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWidth() {

    return getActiveWidget().getScaleX() + SizeUnit.PIXEL.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHeight() {

    return getActiveWidget().getScaleY() + SizeUnit.PIXEL.getValue();
  }

}
