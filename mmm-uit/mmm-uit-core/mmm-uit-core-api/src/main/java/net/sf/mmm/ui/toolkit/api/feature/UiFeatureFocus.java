/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteFocused;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;

/**
 * This is the interface for the {@link UiFeature features} of a focusable object. It can {@link #isFocused()
 * check} and {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteFocused#setFocused(boolean) set} its
 * focus and allows to {@link #addFocusHandler(UiHandlerEventFocus) add} and
 * {@link #removeFocusHandler(UiHandlerEventFocus) remove} instances of {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureFocus extends UiFeature, AttributeWriteFocused {

  /**
   * This method adds the given {@link UiHandlerEventFocus} to this object.
   * 
   * @param handler is the {@link UiHandlerEventFocus} to add.
   */
  void addFocusHandler(UiHandlerEventFocus handler);

  /**
   * This method removes the given {@link UiHandlerEventFocus} from this object.
   * 
   * @param handler is the {@link UiHandlerEventFocus} to remove.
   * @return <code>true</code> if the <code>handler</code> has been removed successfully, <code>false</code>
   *         if it was NOT {@link #addFocusHandler(UiHandlerEventFocus) registered} and nothing has changed.
   */
  boolean removeFocusHandler(UiHandlerEventFocus handler);

}
