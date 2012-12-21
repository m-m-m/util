/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemClickable;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenuItem;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter.UiWidgetAdapterGwtMenuItem;

/**
 * This is the implementation of {@link UiWidgetMenuItemClickable} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuItemClickableGwt extends AbstractUiWidgetMenuItem<UiWidgetAdapterGwtMenuItem> implements
    UiWidgetMenuItemClickable {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMenuItemClickableGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenuItem createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenuItem();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetMenuItemClickable> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuItemClickable.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenuItemClickable create(AbstractUiContext context) {

      return new UiWidgetMenuItemClickableGwt(context);
    }

  }

}
