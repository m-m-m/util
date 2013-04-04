/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt;

import net.sf.mmm.client.ui.base.widget.AbstractUiDisplay;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.UiDisplay} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDisplayGwt extends AbstractUiDisplay {

  /**
   * The constructor.
   */
  public UiDisplayGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    return JavaScriptUtil.getInstance().getScreenWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return JavaScriptUtil.getInstance().getScreenHeight();
  }

}
