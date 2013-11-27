/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetCollapsableSection;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtCollapsableSection;

/**
 * This is the implementation of {@link UiWidgetCollapsableSection} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCollapsableSectionGwt extends
    AbstractUiWidgetCollapsableSection<UiWidgetAdapterGwtCollapsableSection> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetCollapsableSectionGwt(UiContext context, UiWidgetAdapterGwtCollapsableSection widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCollapsableSection createWidgetAdapter() {

    return new UiWidgetAdapterGwtCollapsableSection();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetCollapsableSection> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetCollapsableSection.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetCollapsableSection create(UiContext context) {

      return new UiWidgetCollapsableSectionGwt(context, null);
    }

  }

}
