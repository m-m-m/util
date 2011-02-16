/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that is selectable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteSelectionFlag extends UiReadSelectionFlag {

  /**
   * This method sets the selection status of this object.<br>
   * 
   * NOTE: This method does NOT
   * {@link net.sf.mmm.ui.toolkit.api.event.UIActionListener#invoke(net.sf.mmm.ui.toolkit.api.UINode, net.sf.mmm.ui.toolkit.api.event.ActionType) invoke}
   * an action event.
   * 
   * @param selected - if <code>true</code> the object will be selected, else
   *        it will be deselected.
   */
  void setSelected(boolean selected);

}
