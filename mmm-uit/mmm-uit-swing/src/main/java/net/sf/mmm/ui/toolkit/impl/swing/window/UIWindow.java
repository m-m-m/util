/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Dialog;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.sf.mmm.ui.toolkit.api.UIFactory;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIDialog;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.impl.awt.UIWindowImpl;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the UIWindow interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWindow extends UIWindowImpl {

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the
   *        {@link net.sf.mmm.ui.toolkit.api.UIObject#getFactory() factory}
   *        instance.
   * @param parentObject
   *        is the {@link net.sf.mmm.ui.toolkit.api.UINode#getParent() parent}
   *        that created this object. It may be <code>null</code>.
   */
  public UIWindow(UIFactory uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#showMessage(java.lang.String,
   *      java.lang.String, net.sf.mmm.ui.toolkit.api.window.MessageType)
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
    JOptionPane.showMessageDialog(getAwtWindow(), message, title, type);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#showQuestion(java.lang.String,
   *      java.lang.String)
   */
  public boolean showQuestion(String question, String title) {

    int result = JOptionPane.showConfirmDialog(getAwtWindow(), question, title,
        JOptionPane.YES_NO_OPTION);
    return (result == JOptionPane.YES_OPTION);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.window.UIWindow#createDialog(java.lang.String,
   *      boolean, boolean)
   */
  public UIDialog createDialog(String title, boolean modal, boolean resizeable) {

    JDialog jDialog = null;
    if (getType() == UIFrame.TYPE) {
      jDialog = new JDialog((Frame) getAwtWindow());
    } else {
      jDialog = new JDialog((Dialog) getAwtWindow());
    }
    jDialog.setModal(modal);
    jDialog.setResizable(resizeable);
    UIDialogImpl dialog = new UIDialogImpl((UIFactorySwing) getFactory(), this, jDialog);
    dialog.setTitle(title);
    return dialog;
  }

}
