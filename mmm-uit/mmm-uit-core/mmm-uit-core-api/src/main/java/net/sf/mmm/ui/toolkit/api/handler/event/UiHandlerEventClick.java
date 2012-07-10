/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.event;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onClick(boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerEventClick extends UiHandlerEvent {

  /**
   * This method is invoked if an UI object (a button, menu-item, etc.) has been clicked.
   * 
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.ui.toolkit.api.feature.UiFeatureClickable#click() click was triggered by the
   *        program}, <code>false</code> if the click was performed by the end-user.
   */
  void onClick(boolean programmatic);

}
