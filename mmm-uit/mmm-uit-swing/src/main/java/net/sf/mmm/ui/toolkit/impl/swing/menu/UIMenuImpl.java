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
   * @see net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu#createMenuItem(java.lang.String,
   *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
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
   * @see net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu#createSubMenu(java.lang.String)
   */
  protected UIMenu createSubMenu(String name) {

    JMenu subMenu = new JMenu(name);
    this.menu.add(subMenu);
    return new UIMenuImpl((UIFactorySwing) getFactory(), this, subMenu);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getText()
   */
  public String getText() {

    return this.menu.getName();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenu#addSeparator()
   */
  public void addSeparator() {

    this.menu.add(new JSeparator());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getStyle()
   */
  public ButtonStyle getStyle() {

    return ButtonStyle.DEFAULT;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#isSelected()
   */
  public boolean isSelected() {

    return false;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#setSelected(boolean)
   */
  public void setSelected(boolean selected) {

  // nothing to do!
  }

}
