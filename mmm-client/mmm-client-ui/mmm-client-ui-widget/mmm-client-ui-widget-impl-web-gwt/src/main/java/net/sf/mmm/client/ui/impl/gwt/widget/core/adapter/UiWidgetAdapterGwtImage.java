/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterImage;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.Image;

/**
 * This is the implementation of {@link UiWidgetAdapterImage} using GWT based on {@link Image}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtImage extends UiWidgetAdapterGwtWidget<Image> implements UiWidgetAdapterImage {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtImage() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Image createToplevelWidget() {

    return new Image();
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

    getToplevelWidget().setAltText(altText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    getToplevelWidget().setUrl(url);
  }

}
