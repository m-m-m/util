/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventValueChange;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onValueChange(UiEventValueChange)}.
 * 
 * @param <VALUE> is the generic type of the changed
 *        {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiHandlerEventValueChange<VALUE> implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.VALUE_CHANGE) {
      onValueChange((UiEventValueChange<VALUE>) event);
    }
  }

  /**
   * This method is invoked if an UI object has changed its
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value}.
   * 
   * @param event is the {@link UiEventValueChange value change event}.
   */
  public abstract void onValueChange(UiEventValueChange<VALUE> event);

}
