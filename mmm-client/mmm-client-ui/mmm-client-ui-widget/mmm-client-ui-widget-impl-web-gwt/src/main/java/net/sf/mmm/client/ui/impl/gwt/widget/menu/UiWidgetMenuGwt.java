/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenu;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter.UiWidgetAdapterGwtMenu;

/**
 * This is the implementation of {@link UiWidgetMenu} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuGwt extends AbstractUiWidgetMenu<UiWidgetAdapterGwtMenu> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMenuGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenu createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenu();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetMenu> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenu.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenu create(AbstractUiContext context) {

      return new UiWidgetMenuGwt(context);
    }

  }

}
