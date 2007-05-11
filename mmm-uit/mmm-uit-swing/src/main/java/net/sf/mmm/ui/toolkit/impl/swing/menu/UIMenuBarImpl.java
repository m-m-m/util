/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import java.awt.ComponentOrientation;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.ScriptOrientation;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenuBar;
import net.sf.mmm.ui.toolkit.base.window.AbstractUIWindow;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIMenuBar interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuBarImpl extends AbstractUIMenuBar {

  /** the swing menu bar */
  private JMenuBar menuBar;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param jMenuBar
   *        is the swing menu bar to wrap.
   */
  public UIMenuBarImpl(UIFactorySwing uiFactory, AbstractUIWindow parentObject, JMenuBar jMenuBar) {

    super(uiFactory, parentObject);
    this.menuBar = jMenuBar;
  }

  /**
   * {@inheritDoc}
   */
  protected UIMenu createMenu(String name) {

    JMenu menu = new JMenu(name);
    this.menuBar.add(menu);
    return new UIMenuImpl((UIFactorySwing) getFactory(), this, menu);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh() {
  
    super.refresh();
    ScriptOrientation orientation = getFactory().getScriptOrientation();
    if (orientation.isLeftToRight()) {
      this.menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    } else {
      this.menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
  }
  
}
