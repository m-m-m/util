/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.feature;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;

/**
 * This is the interface for the {@link UiFeature features} of a focusable object. It can {@link #hasFocus()
 * check} and {@link #focus() set} its focus and allows to {@link #addFocusHandler(UiHandlerEventFocus) add}
 * and {@link #removeFocusHandler(UiHandlerEventFocus) remove} instances of {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFeatureFocus extends UiFeature, AttributeWriteHandlerObserver {

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

  /**
   * @return <code>true</code> if this object currently has the focus, <code>false</code> otherwise.
   */
  boolean hasFocus();

  /**
   * This method programmatically sets the focus. This will
   * {@link UiHandlerEventFocus#onFocusChange(UiFeatureFocus, boolean, boolean) notify} all
   * {@link #addFocusHandler(UiHandlerEventFocus) registered} {@link UiHandlerEventFocus listeners}.
   */
  void focus();

}
