/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core.adapter;

import net.sf.mmm.ui.toolkit.base.widget.core.adapter.UiWidgetAdapterImage;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.Image;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link Image}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtImage extends UiWidgetAdapterGwtWidget<Image> implements UiWidgetAdapterImage<Image> {

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
  protected Image createWidget() {

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

    getWidget().setAltText(altText);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUrl(String url) {

    getWidget().setUrl(url);
  }

}
