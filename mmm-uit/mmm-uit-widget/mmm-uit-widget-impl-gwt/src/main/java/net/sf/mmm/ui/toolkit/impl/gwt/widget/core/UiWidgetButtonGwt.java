/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetButton;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.core.AbstractUiWidgetButton;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtButton;

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
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetButtonGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
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
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetButton> {

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
    public UiWidgetButton create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetButtonGwt(factory);
    }

  }

}
