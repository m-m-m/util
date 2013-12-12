/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureOpenClose;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#OPEN}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventOpen extends UiEventOpenOrClose {

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventOpen(UiFeatureOpenClose source, boolean programmatic) {

    super(source, programmatic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.OPEN;
  }

}
