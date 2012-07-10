/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.handler.event;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClickable;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;

/**
 * This is the implementation of {@link AbstractEventSender} for {@link UiHandlerEventClick}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ClickEventSender extends AbstractEventSender<UiHandlerEventClick, UiFeatureClickable> implements
    UiHandlerEventClick {

  /**
   * The constructor.
   * 
   * @param source is the {@link #getSource() source}.
   */
  public ClickEventSender(UiFeatureClickable source) {

    super(UiHandlerEventClick.class, source);
  }

  /**
   * {@inheritDoc}
   */
  public void onClick(boolean programmatic) {

    before();
    for (UiHandlerEventClick handler : getHandlers()) {
      handler.onClick(programmatic);
    }
    after();
  }

}
