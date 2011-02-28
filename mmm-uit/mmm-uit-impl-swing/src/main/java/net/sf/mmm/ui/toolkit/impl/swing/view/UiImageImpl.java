/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view;

import javax.swing.ImageIcon;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiImage;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiImage} interface using Swing as the
 * underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiImageImpl extends AbstractUiImage {

  /** the wrapped picture */
  private final ImageIcon picture;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param imageUrl is the URL to the image data.
   */
  public UiImageImpl(UIFactorySwing uiFactory, UiFileAccess imageUrl) {

    super(uiFactory);
    this.picture = new ImageIcon(imageUrl.getUrl());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return this.picture.getIconWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return this.picture.getIconHeight();
  }

  /**
   * This method gets the native Swing picture.
   * 
   * @return the Swing icon.
   */
  public ImageIcon getSwingIcon() {

    return this.picture;
  }

}
