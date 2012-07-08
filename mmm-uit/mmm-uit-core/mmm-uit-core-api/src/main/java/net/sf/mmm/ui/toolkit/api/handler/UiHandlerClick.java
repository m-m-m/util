/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler;


/**
 * This is the interface for a listener that gets {@link #onClick(boolean) notified} if a UI object (e.g. a
 * Button) has been clicked.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerClick extends UiHandler {

  /**
   * This method is invoked if an UI object has been clicked.
   * 
   * @param programmatic - <code>true</code> if the change was
   *        {@link net.sf.mmm.ui.toolkit.api.feature.UiFeatureClickable#click() triggered by the program},
   *        <code>false</code> if the change was performed by the end-user.
   */
  void onClick(boolean programmatic);

}
