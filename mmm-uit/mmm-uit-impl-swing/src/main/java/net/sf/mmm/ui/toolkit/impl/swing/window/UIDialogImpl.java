/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.UIComposite;
import net.sf.mmm.ui.toolkit.api.window.UIDialog;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBarImpl;

/**
 * This class is the implementation of the UIDialog interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDialogImpl extends UIWindow implements UIDialog {

  /** the swing dialog */
  private final JDialog dialog;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param jDialog is the swing dialog to wrap.
   */
  public UIDialogImpl(UIFactorySwing uiFactory, UINode parentObject, JDialog jDialog) {

    super(uiFactory, parentObject);
    this.dialog = jDialog;
  }

  /**
   * {@inheritDoc}
   */
  protected Window getAwtWindow() {

    return this.dialog;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.dialog.getTitle();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String newTitle) {

    this.dialog.setTitle(newTitle);
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
  public boolean isModal() {

    return this.dialog.isModal();
  }

  /**
   * {@inheritDoc}
   */
  protected UIMenuBarImpl createMenuBar() {

    JMenuBar menuBar = this.dialog.getJMenuBar();
    if (menuBar == null) {
      menuBar = new JMenuBar();
      this.dialog.setJMenuBar(menuBar);
    }
    return new UIMenuBarImpl((UIFactorySwing) getFactory(), this, menuBar);
  }

  /**
   * {@inheritDoc}
   */
  public void setComposite(UIComposite newComposite) {

    JComponent jComponent = ((AbstractUIComponent) newComposite).getSwingComponent();
    this.dialog.setContentPane(jComponent);
    registerComposite(newComposite);
  }

}
