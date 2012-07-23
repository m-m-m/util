/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.core.AbstractUiWidgetImage;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtImage;

/**
 * This is the implementation of {@link UiWidgetImage} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetImageGwt extends AbstractUiWidgetImage<UiWidgetAdapterGwtImage> {

  /**
   * The constructor.
   */
  public UiWidgetImageGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtImage createWidgetAdapter() {

    return new UiWidgetAdapterGwtImage();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetImage> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetImage.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetImage create() {

      return new UiWidgetImageGwt();
    }

  }

}
