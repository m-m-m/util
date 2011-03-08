/* $Id: AbstractUIMenu.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.event.UiEventListener;
import net.sf.mmm.ui.toolkit.api.feature.UiAction;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;

/**
 * This is the base implementation of the UIMenu interface.
 * 
 * @param <N> is the generic type of the {@link #getNativeUiObject() native UI
 *        object}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiMenu extends AbstractUiNode implements UiMenu {

  /** the menu entries */
  private final List<UiMenuItem> items;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiMenu(AbstractUiFactory uiFactory) {

    super(uiFactory);
    this.items = new ArrayList<UiMenuItem>();
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public int getItemCount() {

    return this.items.size();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<UiMenuItem> getItems() {

    return this.items.iterator();
  }

  /**
   * {@inheritDoc}
   */
  public UiMenuItem addItem(String name, UiEventListener action) {

    return addItem(name, action, ButtonStyle.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public UiMenuItem addItem(String name, ButtonStyle style) {

    return addItem(name, null, style);
  }

  /**
   * {@inheritDoc}
   */
  public UiMenuItem addItem(UiAction uiAction) {

    UiMenuItem item = addItem(uiAction.getName(), uiAction.getActionListener(),
        uiAction.getButtonStyle());
    UiImage icon = uiAction.getIcon();
    if (icon != null) {
      // item.setIcon(icon);
    }
    String id = uiAction.getId();
    if (id != null) {
      item.setId(id);
    }
    return item;

  }

  /**
   * {@inheritDoc}
   */
  public UiMenuItem addItem(String name, UiEventListener action, ButtonStyle style) {

    UiMenuItem menuItem = createMenuItem(name, style);
    this.items.add(menuItem);
    if (action != null) {
      menuItem.addListener(action);
    }
    return menuItem;
  }

  /**
   * This method creates a new menu item.
   * 
   * @see net.sf.mmm.ui.toolkit.api.view.menu.UiMenu#addItem(java.lang.String,
   *      net.sf.mmm.ui.toolkit.api.event.UiEventListener)
   * 
   * @param name is the name of the menu item.
   * @param style is the style defining how the item is visualized and behaves.
   * @return the created menu item.
   */
  protected abstract UiMenuItem createMenuItem(String name, ButtonStyle style);

  /**
   * {@inheritDoc}
   */
  public UiMenu addSubMenu(String name) {

    UiMenu menu = createSubMenu(name);
    this.items.add(menu);
    return menu;
  }

  /**
   * This method creates a new sub menu.
   * 
   * @see net.sf.mmm.ui.toolkit.api.view.menu.UiMenu#addSubMenu(java.lang.String)
   * 
   * @param name is the name of the sub-menu.
   * @return the created sub-menu.
   */
  protected abstract UiMenu createSubMenu(String name);

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return ButtonStyle.DEFAULT;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    // nothing to do here
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    for (UiMenuItem menuItem : this.items) {
      ((AbstractUiNode) menuItem).refresh(event);
    }
  }

}
