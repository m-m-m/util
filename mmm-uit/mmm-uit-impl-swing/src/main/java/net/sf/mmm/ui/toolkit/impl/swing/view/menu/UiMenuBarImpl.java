/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.menu;

import java.awt.ComponentOrientation;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenuBar;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;

/**
 * This class is the implementation of the UIMenuBar interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UiMenuBarImpl extends AbstractUiMenuBar<JMenuBar> {

  /** @see #getAdapter() */
  private final UiNodeAdapterSwing<JMenuBar> adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param menuBar is the swing menu bar to wrap.
   */
  public UiMenuBarImpl(UiFactorySwing uiFactory, JMenuBar menuBar) {

    super(uiFactory);
    this.adapter = new UiNodeAdapterSwing<JMenuBar>(this, menuBar);
    updateOrientation();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiNodeAdapterSwing<JMenuBar> getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenu createMenu(String name) {

    JMenu menu = new JMenu(name);
    getDelegate().add(menu);
    return new UiMenuImpl((UiFactorySwing) getFactory(), menu);
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
