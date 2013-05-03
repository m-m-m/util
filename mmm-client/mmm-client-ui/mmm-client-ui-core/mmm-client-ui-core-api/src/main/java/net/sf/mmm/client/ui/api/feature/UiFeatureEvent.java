/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.feature;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;

/**
 * This is the interface for the {@link UiFeature features} of a object sending generic events. It can
 * {@link #addEventHandler(UiHandlerEvent) add} and {@link #removeEventHandler(UiHandlerEvent) remove}
 * instances of {@link UiHandlerEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureEvent extends UiFeature {

  /**
   * This method adds the given {@link UiHandlerEvent} to this object.
   * 
   * @param handler is the {@link UiHandlerEvent} to add.
   */
  void addEventHandler(UiHandlerEvent handler);

  /**
   * This method removes the given {@link UiHandlerEvent} from this object.
   * 
   * @param handler is the {@link UiHandlerEvent} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addEventHandler(UiHandlerEvent) registered} and nothing has changed.
   */
  boolean removeEventHandler(UiHandlerEvent handler);

}
