/* $Id: UIMenuItemImpl.java 976 2011-03-02 21:36:59Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.menu;

import java.awt.ComponentOrientation;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenuItem;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This class is the implementation of the UIMenuItem interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiMenuItemImpl extends AbstractUiMenuItem<JMenuItem> {

  /** @see #getButtonStyle() */
  private final ButtonStyle buttonStyle;

  /** @see #getAdapter() */
  private final UiNodeAdapterSwing<JMenuItem> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   * @param name is the {@link #getValue() name} of the menu item.
   * @param itemStyle is the style defining how the item is visualized and
   *        behaves.
   */
  public UiMenuItemImpl(UiFactorySwing uiFactory, UiNode parentObject, String name,
      ButtonStyle itemStyle) {

    super(uiFactory);
    this.buttonStyle = itemStyle;
    JMenuItem item;
    switch (this.buttonStyle) {
      case DEFAULT:
        item = new JMenuItem(name);
        break;
      case CHECK:
        item = new JCheckBoxMenuItem(name);
        break;
      case RADIO:
        item = new JRadioButtonMenuItem(name);
        break;
      case TOGGLE:
        // toggle not supported!
        item = new JCheckBoxMenuItem(name);
        break;
      default :
        throw new IllegalCaseException(ButtonStyle.class, itemStyle);
    }
    this.adapter = new UiNodeAdapterSwing<JMenuItem>(this, item);
    updateOrientation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiNodeAdapterSwing<JMenuItem> getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().addActionListener(getAdapter());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getDelegate().getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return this.buttonStyle;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return getDelegate().isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    getDelegate().setSelected(selected);
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
