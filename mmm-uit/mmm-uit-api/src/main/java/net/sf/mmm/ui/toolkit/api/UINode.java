/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.window.UIFrame;
import net.sf.mmm.ui.toolkit.api.window.UIWindow;

/**
 * This is the abstract interface for a node of the UI. A node is a UI object
 * that has a parent and or children (e.g. a window, widget, etc.).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UINode extends UIObjectRenamed {

  /**
   * This method gets the parent component.
   * 
   * @return the parent component or <code>null</code> if this is a root
   *         frame.
   */
  UINode getParent();

  /**
   * This method gets the parent frame of this component.
   * 
   * @return the parent frame or <code>null</code> if this is a root frame.
   */
  UIFrame getParentFrame();

  /**
   * This method gets the parent window of this component.
   * 
   * @return the parent window or <code>null</code> if this is a root frame.
   */
  UIWindow getParentWindow();

  /**
   * This method adds an action listener to this component. The listener will be
   * notified of changes of this component.
   * 
   * @param listener is the listener to add.
   */
  void addActionListener(UIActionListener listener);

  /**
   * This method removes an action listener from this component.
   * 
   * @param listener is the listener to remove.
   */
  void removeActionListener(UIActionListener listener);

  /**
   * This method refreshes this node. If not explicitly specified in the
   * according sub-interface, there is no need to call this method explicitly.
   */
  void refresh();

}
