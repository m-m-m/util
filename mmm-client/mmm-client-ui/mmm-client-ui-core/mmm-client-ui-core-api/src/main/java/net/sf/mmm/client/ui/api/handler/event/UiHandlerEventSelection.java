/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onSelectionChange(UiEventSelectionChange)}.
 * 
 * @param <VALUE> is the generic type of the
 *        {@link net.sf.mmm.client.ui.api.attribute.AttributeReadSelectedValue#getSelectedValue() selected
 *        value(s)}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventSelection<VALUE> extends UiHandlerEvent {
public abstract class UiHandlerEventSelection<VALUE> implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.SELECTION_CHANGE) {
      onSelectionChange((UiEventSelectionChange<VALUE>) event);
    }
  }

  /**
   * This method is invoked if an UI object has changed its
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeReadSelectedValue#getSelectedValues() selected
   * value(s)}.
   * 
   * @param event is the {@link UiEventSelectionChange selection change event}.
   */
  public abstract void onSelectionChange(UiEventSelectionChange<VALUE> event);

}
