/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} using GWT based on {@link MenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtMenuItemBase extends UiWidgetAdapterGwt<MenuItem> implements
    AttributeWriteLabel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenuItemBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFromParent() {

    // not supported by GWT
    MenuItem item = getWidget();
    MenuBar menu = item.getParentMenu();
    if (menu != null) {
      menu.removeItem(item);
    }
  }

}
