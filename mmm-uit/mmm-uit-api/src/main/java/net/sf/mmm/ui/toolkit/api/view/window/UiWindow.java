/* $Id: UIWindow.java 955 2011-02-16 23:10:16Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteDisposed;
import net.sf.mmm.ui.toolkit.api.attribute.UiWritePosition;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteTitle;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteVisible;
import net.sf.mmm.ui.toolkit.api.common.MessageType;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;

/**
 * This is the abstract interface for a window of the UIFactory.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWindow extends UiNode, UiWriteDisposed, UiWriteSize, UiWriteTitle,
    UiWritePosition, UiWriteVisible {

  /**
   * This method creates a dialog as child of this window.
   * 
   * @param title is the title of the dialog.
   * @param modal - if <code>true</code> all windows of the application are
   *        blocked until the dialog is closed.
   * @param resizeable - if <code>true</code> the dialog can be resized by the
   *        user.
   * @return the created dialog.
   */
  UiDialog createDialog(String title, boolean modal, boolean resizeable);

  /**
   * This method shows a message to the user. This is done by opening a dialog
   * containing the message with an "OK" button.
   * 
   * @param message is the complete and detailed message to show.
   * @param title is a short title.
   * @param messageType classifies the type of the message to show (according
   *        icon).
   */
  void showMessage(String message, String title, MessageType messageType);

  /**
   * This method shows a message to the user. This is done by opening a dialog
   * containing the message and information about the given throwable with an
   * "OK" button.
   * 
   * @param message is the complete and detailed message to show.
   * @param title is a short title.
   * @param messageType classifies the type of the message to show (according
   *        icon).
   * @param throwable is the cause of the actual message.
   */
  void showMessage(String message, String title, MessageType messageType, Throwable throwable);

  /**
   * This method asks a yes/no question to the user. This is done by opening a
   * dialog with the question with "yes" and "no" buttons.
   * 
   * @param question is the complete question to ask including all details the
   *        user should know to be able to answer the question easily.
   * @param title is a short title.
   * @return <code>true</code> if the question is answered with yes,
   *         <code>false</code> otherwise.
   */
  boolean showQuestion(String question, String title);

  /**
   * This method sizes the window according to its preferences.
   */
  void pack();

  /**
   * This method sets the position of the window, so that it is in the center of
   * the screen.
   */
  void centerWindow();

  /**
   * This method gets the composite that is used to display the content of this
   * window.
   * 
   * @return the composite content of this window or <code>null</code> if the
   *         composite has not been set.
   */
  UiComposite<? extends UiElement> getComposite();

  /**
   * This method sets the composite that is used to display the content of this
   * window. The previous composite of this window will be removed.
   * 
   * @param newComposite is the new composite for this window.
   */
  void setComposite(UiComposite<? extends UiElement> newComposite);

}
