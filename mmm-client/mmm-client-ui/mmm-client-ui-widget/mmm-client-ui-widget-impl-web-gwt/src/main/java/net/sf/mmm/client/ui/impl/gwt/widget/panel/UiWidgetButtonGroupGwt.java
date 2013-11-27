/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonGroup;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetButtonGroup;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtButtonGroup;

/**
 * This is the implementation of {@link UiWidgetButtonGroup} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetButtonGroupGwt extends AbstractUiWidgetButtonGroup<UiWidgetAdapterGwtButtonGroup> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetButtonGroupGwt(UiContext context, UiWidgetAdapterGwtButtonGroup widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtButtonGroup createWidgetAdapter() {

    return new UiWidgetAdapterGwtButtonGroup();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetButtonGroup> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetButtonGroup.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetButtonGroup create(UiContext context) {

      return new UiWidgetButtonGroupGwt(context, null);
    }
  }

}
