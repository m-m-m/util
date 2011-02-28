/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.AbstractUiObject;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiImage} interface.<br>
 * Set initial {@link #setSize(int, int) site} in constructor of the
 * implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiImage extends AbstractUiObject implements UiImage {

  /** the current width (may scale) */
  private int width;

  /** the current height (may scale) */
  private int height;

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.base.AbstractUiFactory} instance.
   */
  public AbstractUiImage(AbstractUiFactory uiFactory) {

    super(uiFactory);
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
  public void setSize(int newWidth, int newHeight) {

    this.width = newWidth;
    this.height = newHeight;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    return this.height;
  }

}
