/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;

/**
 * This is the {@link UiHandlerEvent} for the action
 * {@link #onFocusChange(AttributeReadFocused, boolean, boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerEventFocus extends UiHandlerEvent {

  /**
   * This method is invoked if an UI object (a text input, etc.) has changed its focus. In that case there
   * will always be two events: The object that previously had the focus will receive an event that its focus
   * has been lost and the object that has been focused will receive and event that its has gained the focus.
   * 
   * @param source is the object that has changed his focus.
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused#setFocused() focus change was
   *        triggered by the program}, <code>false</code> if performed by the end-user (by pressing the [tab]
   *        key or clicking into a widget with the mouse).
   * @param lost - <code>true</code> if the focus has been lost (<em>blur</em>), <code>false</code> if the
   *        focus has been gained (<em>focus</em>).
   */
  // TODO hohwille invert logic from lost to focused
  void onFocusChange(AttributeReadFocused source, boolean programmatic, boolean lost);

}
