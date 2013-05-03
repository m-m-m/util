/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.common.UiEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onValueChange(AttributeReadValue, boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed {@link AttributeReadValue#getValue() value}.
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventValueChange<VALUE> extends UiHandlerEvent {
public abstract class UiHandlerEventValueChange<VALUE> implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void onEvent(UiFeatureEvent source, UiEvent event, boolean programmatic) {

    if (event.getType() == EventType.VALUE_CHANGE) {
      onValueChange((AttributeReadValue<VALUE>) source, programmatic);
    }
  }

  /**
   * This method is invoked if an UI object has changed its {@link AttributeReadValue#getValue() value}.
   * 
   * @param source is the object that has changed its {@link AttributeReadValue#getValue() value}.
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.util.lang.api.attribute.AttributeWriteValue#setValue(Object) change was
   *        triggered by the program}, <code>false</code> if the change was performed by the end-user (e.g by
   *        typing into an input field).
   */
  public abstract void onValueChange(AttributeReadValue<VALUE> source, boolean programmatic);

}
