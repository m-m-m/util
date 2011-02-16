/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

import net.sf.mmm.ui.toolkit.api.UiNode;

/**
 * This class represents an action that is invoked on an interaction of the user
 * in the UIFactory (e.g. hitting a button).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UIActionListener extends EventListener {

  /**
   * This method is called if the user invoked an interaction in a UIComponent.
   * 
   * @param source is the component that invoked the action.
   * @param action is the action that has been invoked.
   */
  void invoke(UiNode source, ActionType action);

}
