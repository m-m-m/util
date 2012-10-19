/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeReadFocused;
import net.sf.mmm.client.ui.api.attribute.AttributeReadHandlerObserver;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FocusEventSender extends AbstractEventSender<UiHandlerEventFocus, UiFeatureFocus> implements
    UiHandlerEventFocus, AttributeReadFocused {

  /** @see #isFocused() */
  private boolean focused;

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   * @param observerSource is the {@link AttributeReadHandlerObserver provider} of a potential
   *        {@link net.sf.mmm.client.ui.api.handler.UiHandlerObserver}.
   */
  public FocusEventSender(UiFeatureFocus source, AttributeReadHandlerObserver observerSource) {

    super(UiHandlerEventFocus.class, source, observerSource);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocusChange(AttributeReadFocused source, boolean programmatic, boolean lost) {

    this.focused = !lost;
    before();
    for (UiHandlerEventFocus handler : getHandlers()) {
      handler.onFocusChange(source, programmatic, lost);
    }
    after();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return this.focused;
  }

}
