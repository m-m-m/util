/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemSeparator;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenu;

/**
 * This is the abstract base implementation of {@link UiWidgetMenu}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenu<ADAPTER extends UiWidgetAdapterMenu> extends
    AbstractUiWidgetDynamicComposite<ADAPTER, UiWidgetMenuItem> implements UiWidgetMenu {

  /** @see #getLabel() */
  private String label;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetMenu(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.label != null) {
      adapter.setLabel(this.label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSeparator() {

    UiWidgetMenuItemSeparator separator = getContext().getWidgetFactory().create(UiWidgetMenuItemSeparator.class);
    addChild(separator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMenu addSubmenu(String submenuLabel) {

    UiWidgetMenu submenu = getContext().getWidgetFactory().create(UiWidgetMenu.class);
    submenu.setLabel(submenuLabel);
    addChild(submenu);
    return submenu;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    if (this.label != null) {
      return this.label;
    }
    return super.getSource();
  }

}
