/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.base.view.UiNodeAdapter;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.view.UiImageImpl;

import com.google.gwt.user.client.ui.ButtonBase;

/**
 * This class is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton}
 * using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiButton extends AbstractUiWidget implements UiButton {

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiNodeAdapter getAdapter() {

    // TODO Auto-generated method stub
    return null;
  }

  /** @see #getImage() */
  private UiImageImpl image;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiButton(UiFactoryGwt uiFactory) {

    super(uiFactory);
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract ButtonBase getNativeUiObject();

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getNativeUiObject().setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getNativeUiObject().getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage image) {

    this.image = (UiImageImpl) image;
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getImage() {

    return this.image;
  }
}
