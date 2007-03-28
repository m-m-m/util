/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIMenu interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuImpl extends AbstractUIMenu {

  /** the swing menu */
  private final JMenu menu;

  /** the button group for radio items */
  private ButtonGroup buttonGroup;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param jMenu
   *        is the swing menu to wrap.
   */
  public UIMenuImpl(UIFactorySwing uiFactory, UINode parentObject, JMenu jMenu) {

    super(uiFactory, parentObject);
    this.menu = jMenu;
    this.buttonGroup = null;
  }

  /**
   * This method gets the unwrapped swing menu.
   * 
   * @return the native swing menu.
   */
  protected JMenu getSwingMenu() {

    return this.menu;
  }

  /**
   * This method gets the button group for this menu.
   * 
   * @return the button group.
   */
  protected ButtonGroup getButtonGroup() {

    if (this.buttonGroup == null) {
      this.buttonGroup = new ButtonGroup();
    }
    return this.buttonGroup;
  }

  /**
   * {@inheritDoc}
   */
  protected UIMenuItem createMenuItem(String name, ButtonStyle style) {

    UIMenuItemImpl item = new UIMenuItemImpl((UIFactorySwing) getFactory(), this, name, style);
    if (style == ButtonStyle.RADIO) {
      getButtonGroup().add(item.getSwingMenuItem());
    }
    this.menu.add(item.getSwingMenuItem());
    return item;
  }

  /**
   * {@inheritDoc}
   */
  protected UIMenu createSubMenu(String name) {

    JMenu subMenu = new JMenu(name);
    this.menu.add(subMenu);
    return new UIMenuImpl((UIFactorySwing) getFactory(), this, subMenu);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.menu.getName();
  }

  /**
   * {@inheritDoc}
   */
  public void addSeparator() {

    this.menu.add(new JSeparator());
  }

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

  // nothing to do!
  }

}
