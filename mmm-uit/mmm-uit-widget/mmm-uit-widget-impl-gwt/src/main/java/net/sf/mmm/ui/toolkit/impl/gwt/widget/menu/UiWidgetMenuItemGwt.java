/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.menu.AbstractUiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtMenuItem;

/**
 * This is the implementation of {@link UiWidgetMenuItem} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuItemGwt extends AbstractUiWidgetMenuItem<UiWidgetAdapterGwtMenuItem> {

  /**
   * The constructor.
   */
  public UiWidgetMenuItemGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenuItem createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenuItem();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetMenuItem> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuItem.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetMenuItem create() {

      return new UiWidgetMenuItemGwt();
    }

  }

}
