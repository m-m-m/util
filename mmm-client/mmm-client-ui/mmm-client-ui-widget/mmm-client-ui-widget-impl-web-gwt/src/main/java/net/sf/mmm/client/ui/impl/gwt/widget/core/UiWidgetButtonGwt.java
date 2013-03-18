/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetButton;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtButton;

/**
 * This is the implementation of {@link UiWidgetButton} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetButtonGwt extends AbstractUiWidgetButton<UiWidgetAdapterGwtButton> {

  /** @see #getImage() */
  private UiWidgetImageGwt image;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetButtonGwt(AbstractUiContext context) {

    super(context);
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtButton createWidgetAdapter() {

    return new UiWidgetAdapterGwtButton();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    return this.image;
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetButton> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetButton.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetButton create(AbstractUiContext context) {

      return new UiWidgetButtonGwt(context);
    }

  }

}
