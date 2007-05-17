/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import java.awt.ComponentOrientation;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.awt.UIAwtNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIMenuItem interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuItemImpl extends UIAwtNode implements UIMenuItem {

  /** the swing menu item */
  private final JMenuItem item;

  /** the style of this item */
  private final ButtonStyle style;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param name
   *        is the {@link #getText() name} of the menu item.
   * @param itemStyle
   *        is the style defining how the item is visualized and behaves.
   */
  public UIMenuItemImpl(UIFactorySwing uiFactory, UINode parentObject, String name, ButtonStyle itemStyle) {

    super(uiFactory, parentObject);
    this.style = itemStyle;
    switch (this.style) {
      case DEFAULT:
        this.item = new JMenuItem(name);
        break;
      case CHECK:
        this.item = new JCheckBoxMenuItem(name);
        break;
      case RADIO:
        this.item = new JRadioButtonMenuItem(name);
        break;
      case TOGGLE:
        // toggle not supported!
        this.item = new JCheckBoxMenuItem(name);
        break;
      default :
        throw new IllegalArgumentException("Unknown style " + itemStyle);
    }
    updateOrientation();
  }

  /**
   * This method gets the unwrapped swing menu-item.
   * 
   * @return the native swing item.
   */
  protected JMenuItem getSwingMenuItem() {

    return this.item;
  }

  /**
   * {@inheritDoc}
   */
  protected boolean doInitializeListener() {

    this.item.addActionListener(createActionListener());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.item.getText();
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
  public ButtonStyle getButtonStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return this.item.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.item.setSelected(selected);
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
    this.item.setComponentOrientation(componentOrientation);
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
