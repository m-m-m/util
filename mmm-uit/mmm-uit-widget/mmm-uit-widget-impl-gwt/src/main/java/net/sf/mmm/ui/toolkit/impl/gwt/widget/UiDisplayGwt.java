/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiDisplay;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiDisplay} for GWT.
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
  public int getWidthInPixel() {

    return JavaScriptUtil.getInstance().getScreenWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return JavaScriptUtil.getInstance().getScreenHeight();
  }

}
