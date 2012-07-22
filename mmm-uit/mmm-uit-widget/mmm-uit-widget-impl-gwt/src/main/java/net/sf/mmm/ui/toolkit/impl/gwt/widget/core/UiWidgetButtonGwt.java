/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetButton;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.Button;

/**
 * This is the implementation of {@link UiWidgetButton} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetButtonGwt extends UiWidgetAtomicClickableGwt<Button> implements UiWidgetButton {

  /** @see #getImage() */
  private UiWidgetImageGwt image;

  /**
   * The constructor.
   */
  public UiWidgetButtonGwt() {

    super();
    this.image = null;
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
  public void setLabel(String label) {

    getWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return getWidget().getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    return this.image;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    if (this.image == image) {
      return;
    }
    if (this.image != null) {
      getWidget().getElement().removeChild(this.image.getWidget().getElement());
    }
    this.image = (UiWidgetImageGwt) image;
    if (this.image != null) {
      getWidget().getElement().insertFirst(this.image.getWidget().getElement());
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetButton> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetButton.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetButton create() {

      return new UiWidgetButtonGwt();
    }

  }

}
