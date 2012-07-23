/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterButton;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link Button}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtButton extends UiWidgetAdapterGwtFocusWidget<Button> implements
    UiWidgetAdapterButton<Button> {

  /** @see #setImage(UiWidgetImage) */
  private Image image;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtButton() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Button createWidget() {

    return new Button();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage imageWidget) {

    Image newImage = null;
    if (imageWidget != null) {
      newImage = getWidget(imageWidget, Image.class);
    }
    Element element = getWidget().getElement();
    if (this.image == null) {
      if (newImage != null) {
        element.insertFirst(newImage.getElement());
      }
    } else {
      if (newImage == null) {
        element.removeChild(this.image.getElement());
      } else {
        element.replaceChild(newImage.getElement(), this.image.getElement());
      }
    }
    this.image = newImage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getWidget().setText(label);
  }

}
