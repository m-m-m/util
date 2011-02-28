/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.window;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.view.window.UiDialog;
import net.sf.mmm.ui.toolkit.api.view.window.UiFrame;
import net.sf.mmm.ui.toolkit.api.view.window.UiWindow;
import net.sf.mmm.ui.toolkit.base.AbstractUiFactory;
import net.sf.mmm.ui.toolkit.impl.awt.AbstractUiWindowAwt;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIWindow interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWindowImpl extends AbstractUiWindowAwt {

  /**
   * The constructor.
   * 
   * @param uiFactory is the
   *        {@link net.sf.mmm.ui.toolkit.api.UiObject#getFactory() factory}
   *        instance.
   * @param parentObject is the {@link #getParent() parent} of this object. It
   *        may be <code>null</code>.
   */
  public AbstractUiWindowImpl(AbstractUiFactory uiFactory, UiWindow parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * {@inheritDoc}
   */
  public void showMessage(String message, String title, MessageType messageType) {

    int type = JOptionPane.PLAIN_MESSAGE;
    if (messageType == MessageType.ERROR) {
      type = JOptionPane.ERROR_MESSAGE;
    } else if (messageType == MessageType.WARNING) {
      type = JOptionPane.WARNING_MESSAGE;
    } else if (messageType == MessageType.INFO) {
      type = JOptionPane.INFORMATION_MESSAGE;
    }
    JOptionPane.showMessageDialog(getNativeWindow(), message, title, type);
  }

  /**
   * {@inheritDoc}
   */
  public boolean showQuestion(String question, String title) {

    int result = JOptionPane.showConfirmDialog(getNativeWindow(), question, title,
        JOptionPane.YES_NO_OPTION);
    return (result == JOptionPane.YES_OPTION);
  }

  /**
   * {@inheritDoc}
   */
  public UiDialog createDialog(String title, boolean modal, boolean resizeable) {

    JDialog jDialog = null;
    if (getType() == UiFrame.TYPE) {
      jDialog = new JDialog((Frame) getNativeWindow());
    } else {
      jDialog = new JDialog((Dialog) getNativeWindow());
    }
    jDialog.setModal(modal);
    jDialog.setResizable(resizeable);
    UIDialogImpl dialog = new UIDialogImpl((UIFactorySwing) getFactory(), this, jDialog);
    dialog.setTitle(title);
    getFactory().addWindow(dialog);
    return dialog;
  }

}
