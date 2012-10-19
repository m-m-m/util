/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuItemClickable;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.menu.AbstractUiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.menu.adapter.UiWidgetAdapterGwtMenuItem;

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
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetMenuItemClickableGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
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
    public UiWidgetMenuItemClickable create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetMenuItemClickableGwt(factory);
    }

  }

}
