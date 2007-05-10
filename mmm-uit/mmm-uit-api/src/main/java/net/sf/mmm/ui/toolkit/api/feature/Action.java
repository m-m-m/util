/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.event.UIActionListener;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the callback interface for an action that can be invoked by a widget
 * or menu-item. It aims to abstract from the GUI element and groups your local
 * code with the functionality you want to make available in the GUI. This
 * allows to change the appearance of the according GUI element (e.g. change
 * from button to menu-item) or reuse the functionality for other parts of the
 * GUI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Action {

  /**
   * This method gets the display name of the action. This is used as
   * {@link net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(String) text}
   * for the widget that visualizes this action.
   * 
   * @return the name of this action.
   */
  String getName();

  /**
   * This method gets the id of this action. It is used as
   * {@link net.sf.mmm.ui.toolkit.api.UIObject#getId() object-id} for the widget
   * that visualizes this action.
   * 
   * @return the id of this action or <code>null</code> if the default id of
   *         the widget should NOT be changed.
   */
  String getId();

  /**
   * This method gets the style of this action.
   * 
   * @return the action style.
   */
  ButtonStyle getButtonStyle();

  /**
   * This method gets the icon used to visualize this action.
   * 
   * @return the icon for this action or <code>null</code> if no icon is
   *         associated.
   */
  UIPicture getIcon();

  /**
   * This method gets the listener that will be called if this action is
   * invoked.
   * 
   * @return the listener for this action.
   */
  UIActionListener getActionListener();

}
