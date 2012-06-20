/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.handler.UiHandlerClick;

/**
 * This is the interface for the features of a clickable object. It can be {@link #click() clicked} and allows
 * to {@link #addClickHandler(UiHandlerClick) add} and {@link #removeClickHandler(UiHandlerClick) remove}
 * {@link UiHandlerClick}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureClickable {

  /**
   * This method adds the given {@link UiHandlerClick} to this object.
   * 
   * @param handler is the {@link UiHandlerClick} to add.
   */
  void addClickHandler(UiHandlerClick handler);

  /**
   * This method removes the given {@link UiHandlerClick} from this object.
   * 
   * @param handler is the {@link UiHandlerClick} to remove.
   * @return <code>true</code> if the <code>listener</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addClickHandler(UiHandlerClick) registered} and nothing has changed.
   */
  boolean removeClickHandler(UiHandlerClick handler);

  /**
   * This method programmatically {@link UiHandlerClick#onClick(boolean) notifies} all
   * {@link #addClickHandler(UiHandlerClick) registered} {@link UiHandlerClick listeners}.
   */
  void click();

}
