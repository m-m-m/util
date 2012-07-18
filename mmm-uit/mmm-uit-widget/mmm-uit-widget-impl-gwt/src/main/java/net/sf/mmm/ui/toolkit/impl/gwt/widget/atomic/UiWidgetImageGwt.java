/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.Image;

/**
 * This is the implementation of {@link UiWidgetImage} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetImageGwt extends UiWidgetAtomicRegularGwt<Image> implements UiWidgetImage {

  /** @see #getAltText() */
  private String altText;

  /** @see #getUrl() */
  private String url;

  /** @see #getWidth() */
  private int width;

  /** @see #getHeight() */
  private int height;

  /**
   * The constructor.
   */
  public UiWidgetImageGwt() {

    super(new Image());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    this.url = url;
    getWidget().setUrl(url);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAltText(String altText) {

    this.altText = altText;
    getWidget().setAltText(altText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(int newWidth, int newHeight) {

    this.width = newWidth;
    this.height = newHeight;
    getWidget().setSize(Integer.toString(newWidth), Integer.toString(newHeight));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidth() {

    return this.width;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeight() {

    return this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAltText() {

    return this.altText;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUrl() {

    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // nothing to do / not supported
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetImage> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetImage.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetImage create() {

      return new UiWidgetImageGwt();
    }

  }

}
