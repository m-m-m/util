/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

import net.sf.mmm.ui.toolkit.api.view.UiNode;

/**
 * This is the interface for a listener that gets {@link #onEvent(UiNode, UiEventType) notified} if a
 * particular {@link UiEventType type of action} occurred.<br/>
 * Some examples:
 * <ul>
 * <li>if a {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} was clicked by the user, a
 * {@link UiEventType#CLICK} is send.</li>
 * <li></li>
 * <li></li>
 * <li></li>
 * <li></li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiEventListener extends EventListener {

  /**
   * This method is called if the user invoked an interaction in a UIComponent.
   * 
   * @param source is the component that invoked the action.
   * @param action is the action that has been invoked.
   */
  void onEvent(UiNode source, UiEventType action);

}
