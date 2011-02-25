/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.menu.UIMenuBarImpl;

/**
 * This class is the implementation of the UIDialog interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIDialogImpl extends AbstractUiWindowImpl implements UiDialog {

  /** the swing dialog */
  private final JDialog dialog;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param jDialog is the swing dialog to wrap.
   */
  public UIDialogImpl(UIFactorySwing uiFactory, UiNode parentObject, JDialog jDialog) {

    super(uiFactory, parentObject);
    this.dialog = jDialog;
  }

  /**
   * {@inheritDoc}
   */
  protected Window getNativeWindow() {

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
  public void setComposite(UiComposite newComposite) {

    JComponent jComponent = ((AbstractUiElement) newComposite).getSwingComponent();
    this.dialog.setContentPane(jComponent);
    registerComposite(newComposite);
  }

}
