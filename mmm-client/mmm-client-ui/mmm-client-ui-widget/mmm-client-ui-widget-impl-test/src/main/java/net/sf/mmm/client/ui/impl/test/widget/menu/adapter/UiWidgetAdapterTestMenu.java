/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.menu.adapter;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenu;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestComposite;

/**
 * This is the implementation of {@link UiWidgetAdapterMenu} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestMenu extends UiWidgetAdapterTestComposite<UiWidgetMenuItem> implements
    UiWidgetAdapterMenu {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestMenu() {

    super();
  }

}
