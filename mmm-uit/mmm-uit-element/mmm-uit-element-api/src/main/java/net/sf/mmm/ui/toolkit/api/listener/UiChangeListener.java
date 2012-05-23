/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.listener;

import net.sf.mmm.ui.toolkit.api.element.UiElement;

/**
 * This is the interface for a listener that gets
 * {@link #onChange(UiElement, boolean) notified} if the
 * {@link UiElement#getValue() value} of an {@link UiElement} has changed.
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <VALUE> is the generic type of the
 *        {@link #onChange(UiElement, boolean) observed}
 *        {@link UiElement#getValue() value}.
 */
public interface UiChangeListener<VALUE> {

  /**
   * This method is invoked if the value of an {@link UiElement} has changed.
   * 
   * @param element is the {@link UiElement} that changed.
   * @param programmatic - <code>true</code> if the change was triggered by the
   *        program, <code>false</code> if the change was performed by the
   *        end-user.
   */
  void onChange(UiElement<VALUE, ?> element, boolean programmatic);

}
