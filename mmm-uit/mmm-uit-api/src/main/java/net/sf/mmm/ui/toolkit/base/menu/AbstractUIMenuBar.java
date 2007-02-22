/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.menu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBar;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.base.AbstractUINode;

/**
 * This is the base implementation of the UIMenuBar interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIMenuBar extends AbstractUINode implements UIMenuBar {

  /** mapps the name of a menu (String) to a menu (UIMenu) */
  private Map<String, UIMenu> menuTable;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactory instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public AbstractUIMenuBar(UIFactory uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.menuTable = new HashMap<String, UIMenu>();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBar#addMenu(java.lang.String)
   */
  public UIMenu addMenu(String name) {

    synchronized (this.menuTable) {
      UIMenu menu = this.menuTable.get(name);
      if (menu == null) {
        // the menu does not already exist and is created here...
        menu = createMenu(name);
        this.menuTable.put(name, menu);
      }
      return this.menuTable.get(name);
    }
  }

  /**
   * This method creates a menu in this menu bar.
   * 
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBar#addMenu(java.lang.String)
   * 
   * @param name
   *        is the name of the menu.
   * @return the created menu.
   */
  protected abstract UIMenu createMenu(String name);

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBar#getMenu(java.lang.String)
   */
  public UIMenu getMenu(String name) {

    return this.menuTable.get(name);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBar#getMenuCount()
   */
  public int getMenuCount() {

    return this.menuTable.size();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBar#getMenus()
   */
  public Iterator<UIMenu> getMenus() {

    return this.menuTable.values().iterator();
  }

}
