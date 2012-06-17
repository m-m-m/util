/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.listener;

import net.sf.mmm.ui.toolkit.api.element.UiElement;

/**
 * This is the interface for a listener that gets
 * {@link #onAction(UiElement, boolean) notified} if an <em>action</em> has been
 * invoked on an {@link UiElement}. Here action typically means that it (e.g. a
 * Button) has been clicked.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiActionListener {

  /**
   * This method is invoked if an action has been invoked on an
   * {@link UiElement}.
   * 
   * @param element is the {@link UiElement} that has been clicked.
   * @param programmatic - <code>true</code> if the change was triggered by the
   *        program, <code>false</code> if the change was performed by the
   *        end-user.
   */
  void onAction(UiElement<?, ?> element, boolean programmatic);

}
