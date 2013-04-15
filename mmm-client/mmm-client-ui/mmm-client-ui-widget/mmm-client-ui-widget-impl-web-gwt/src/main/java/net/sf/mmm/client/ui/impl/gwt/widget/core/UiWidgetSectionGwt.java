/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSection;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetSection;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtSection;

/**
 * This is the implementation of {@link UiWidgetSection} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetSectionGwt extends AbstractUiWidgetSection<UiWidgetAdapterGwtSection> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetSectionGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtSection createWidgetAdapter() {

    return new UiWidgetAdapterGwtSection();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetSection> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetSection.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetSection create(UiContext context) {

      return new UiWidgetSectionGwt(context);
    }

  }

}
