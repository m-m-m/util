/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;

/**
 * This is the implementation of {@link UiWidgetLabel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLabelTestImpl extends AbstractUiWidgetLabel<UiWidgetAdapterTest> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLabelTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTest createWidgetAdapter() {

    return new UiWidgetAdapterTest();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLabel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLabel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLabel create(UiContext context) {

      return new UiWidgetLabelTestImpl(context);
    }

  }

}
