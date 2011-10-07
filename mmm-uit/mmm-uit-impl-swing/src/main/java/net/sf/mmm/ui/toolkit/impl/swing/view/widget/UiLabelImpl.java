/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JLabel;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiLabelImpl extends AbstractUiWidgetSwing<JLabel> implements UiLabel {

  /** @see #getImage() */
  private UiImageImpl image;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiLabelImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JLabel());
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
  public void setValue(String text) {

    getAdapter().getDelegate().setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getAdapter().getDelegate().getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.image = (UiImageImpl) newIcon;
    if (this.image == null) {
      getAdapter().getDelegate().setIcon(null);
    } else {
      getAdapter().getDelegate().setIcon(this.image.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getImage() {

    return this.image;
  }

}
