/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtMenuBar;

/**
 * This is the implementation of {@link UiWidgetMenuBar} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuBarGwt extends AbstractUiWidgetDynamicComposite<UiWidgetAdapterGwtMenuBar, UiWidgetMenu>
    implements UiWidgetMenuBar {

  /**
   * The constructor.
   */
  public UiWidgetMenuBarGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenuBar createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenuBar();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetMenuBar> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuBar.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetMenuBar create() {

      return new UiWidgetMenuBarGwt();
    }

  }

}
