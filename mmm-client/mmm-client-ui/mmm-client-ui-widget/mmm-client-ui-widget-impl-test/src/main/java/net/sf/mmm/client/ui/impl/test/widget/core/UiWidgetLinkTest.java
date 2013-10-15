/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLink;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetLink;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;

/**
 * This is the implementation of {@link UiWidgetLink} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLinkTest extends AbstractUiWidgetLink<UiWidgetAdapterTest> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLinkTest(UiContext context) {

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
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLink> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLink.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLink create(UiContext context) {

      return new UiWidgetLinkTest(context);
    }

  }

}
