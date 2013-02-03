/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.menu;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuBar;

/**
 * This is the abstract base implementation of {@link UiWidgetMenu}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenuBar<ADAPTER extends UiWidgetAdapterMenuBar> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetMenu> implements UiWidgetMenuBar {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetMenuBar(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMenu addMenu(String label) {

    UiWidgetMenu menu = getContext().getWidgetFactory().create(UiWidgetMenu.class);
    menu.setLabel(label);
    addChild(menu);
    return menu;
  }

}
