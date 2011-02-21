/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt;

import net.sf.mmm.ui.toolkit.base.AbstractUIPicture;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiImage} interface using SWT as
 * the underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIPictureImpl extends AbstractUIPicture {

  /** the native SWT image object */
  private final Image image;

  /** the image data */
  private final ImageData data;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param imageUrl is the URL to the image data.
   */
  public UIPictureImpl(UIFactorySwt uiFactory, String imageUrl) {

    super(uiFactory);
    this.data = new ImageData(imageUrl);
    this.image = new Image(uiFactory.getDisplay().getSwtDisplay(), this.data);
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    return this.data.height;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    return this.data.width;
  }

  /**
   * This method gets the native SWT picture.
   * 
   * @return the SWT image.
   */
  public Image getSwtImage() {

    return this.image;
  }

}
