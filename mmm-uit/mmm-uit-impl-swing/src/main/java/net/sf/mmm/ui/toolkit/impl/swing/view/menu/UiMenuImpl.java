/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.menu;

import java.awt.ComponentOrientation;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenu;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;

/**
 * This class is the implementation of the UIMenu interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiMenuImpl extends AbstractUiMenu<JMenu> {

  /** @see #getAdapter() */
  private final UiNodeAdapterSwing<JMenu> adapter;

  /** the button group for radio items */
  private ButtonGroup buttonGroup;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param menu is the swing menu to wrap.
   */
  public UiMenuImpl(UiFactorySwing uiFactory, JMenu menu) {

    super(uiFactory);
    this.adapter = new UiNodeAdapterSwing<JMenu>(this, menu);
    this.buttonGroup = null;
    updateOrientation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiNodeAdapterSwing<JMenu> getAdapter() {

    return this.adapter;
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
  @Override
  protected UiMenuItemImpl createMenuItem(String name, ButtonStyle style) {

    UiMenuItemImpl item = new UiMenuItemImpl((UiFactorySwing) getFactory(), this, name, style);
    JMenuItem jMenuItem = item.getAdapter().getDelegate();
    if (style == ButtonStyle.RADIO) {
      getButtonGroup().add(jMenuItem);
    }
    getDelegate().add(jMenuItem);
    return item;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenuImpl createSubMenu(String name) {

    JMenu subMenu = new JMenu(name);
    getDelegate().add(subMenu);
    return new UiMenuImpl((UiFactorySwing) getFactory(), subMenu);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getDelegate().getName();
  }

  /**
   * {@inheritDoc}
   */
  public void addSeparator() {

    getDelegate().add(new JSeparator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ButtonStyle getButtonStyle() {

    return ButtonStyle.DEFAULT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelected() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
    getDelegate().setComponentOrientation(componentOrientation);
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
