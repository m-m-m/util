/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetAtomicWithLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.menu.adapter.UiWidgetAdapterMenuItem;

/**
 * This is the abstract base implementation of {@link UiWidgetMenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenuItem<ADAPTER extends UiWidgetAdapterMenuItem<?>> extends
    AbstractUiWidgetAtomicWithLabel<ADAPTER> implements UiWidgetMenuItem {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetMenuItem(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

}
