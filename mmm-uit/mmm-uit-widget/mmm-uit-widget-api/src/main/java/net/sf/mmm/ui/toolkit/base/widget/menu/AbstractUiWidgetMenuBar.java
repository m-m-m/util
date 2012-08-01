/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterMenuBar;

/**
 * This is the abstract base implementation of {@link UiWidgetMenu}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenuBar<ADAPTER extends UiWidgetAdapterMenuBar<?>> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetMenu> implements UiWidgetMenuBar {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetMenuBar(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMenu addMenu(String label) {

    UiWidgetMenu menu = getFactory().create(UiWidgetMenu.class);
    menu.setLabel(label);
    addChild(menu);
    return menu;
  }

}
