/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view;

import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiImage;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiImage} interface using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiImageImpl extends AbstractUiImage {

  /** @see #getUrl() */
  private String url;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.base.AbstractUiFactory} instance.
   * @param imageUrl is the URL to the image data.
   */
  public UiImageImpl(UiFactoryGwt uiFactory, UiFileAccess imageUrl) {

    super(uiFactory);
    this.url = imageUrl.getUrl();
  }

  /**
   * @return the {@link UiFileAccess#getUrl() URL} of the image.
   */
  public String getUrl() {

    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    // TODO Auto-generated method stub
    return 0;
  }

}
