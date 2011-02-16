/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import java.awt.ComponentOrientation;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuItem;
import net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIMenu interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIMenuImpl extends AbstractUIMenu {

  /** the swing menu */
  private final JMenu menu;

  /** the button group for radio items */
  private ButtonGroup buttonGroup;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param jMenu is the swing menu to wrap.
   */
  public UIMenuImpl(UIFactorySwing uiFactory, UiNode parentObject, JMenu jMenu) {

    super(uiFactory, parentObject);
    this.menu = jMenu;
    this.buttonGroup = null;
    updateOrientation();
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
  protected UiMenuItem createMenuItem(String name, ButtonStyle style) {

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
  protected UiMenu createSubMenu(String name) {

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

  /**
   * This method updates the orientation of the GUI elements.
   */
  protected void updateOrientation() {

    ComponentOrientation componentOrientation;
    if (getFactory().getScriptOrientation().isLeftToRight()) {
      componentOrientation = ComponentOrientation.LEFT_TO_RIGHT;
    } else {
      componentOrientation = ComponentOrientation.RIGHT_TO_LEFT;
    }
    this.menu.setComponentOrientation(componentOrientation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (event.isOrientationModified()) {
      updateOrientation();
    }
  }

}
