/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import javax.inject.Named;

import net.sf.mmm.client.ui.base.widget.AbstractUiDisplay;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.UiDisplay} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiDisplayTest extends AbstractUiDisplay {

  /**
   * The constructor.
   */
  public UiDisplayTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    return 1280;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return 1024;
  }

}
