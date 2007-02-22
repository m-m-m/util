/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.sf.mmm.ui.toolkit.api.UINode;
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
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  protected boolean doInitializeListener() {

    this.item.addActionListener(createActionListener());
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getText()
   */
  public String getText() {

    return this.item.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getStyle()
   */
  public ButtonStyle getStyle() {

    return this.style;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#isSelected()
   */
  public boolean isSelected() {

    return this.item.isSelected();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlag#setSelected(boolean)
   */
  public void setSelected(boolean selected) {

    this.item.setSelected(selected);
  }

}
