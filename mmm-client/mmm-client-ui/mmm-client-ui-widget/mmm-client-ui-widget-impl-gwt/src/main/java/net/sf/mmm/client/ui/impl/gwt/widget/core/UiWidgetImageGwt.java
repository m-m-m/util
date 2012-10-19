/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetImage;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtImage;

/**
 * This is the implementation of {@link UiWidgetImage} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetImageGwt extends AbstractUiWidgetImage<UiWidgetAdapterGwtImage> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetImageGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtImage createWidgetAdapter() {

    return new UiWidgetAdapterGwtImage();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetImage> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetImage.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetImage create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetImageGwt(factory);
    }

  }

}
