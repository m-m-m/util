/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventFocus}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class FocusEventSender extends AbstractEventSender<UiHandlerEventFocus, UiFeatureFocus> implements
    UiHandlerEventFocus {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  public FocusEventSender(UiFeatureFocus source) {

    super(UiHandlerEventFocus.class, source);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocusChange(UiFeatureFocus source, boolean programmatic, boolean lost) {

    before();
    for (UiHandlerEventFocus handler : getHandlers()) {
      handler.onFocusChange(source, programmatic, lost);
    }
    after();
  }

}
