/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#COLLAPSE}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventCollapse extends UiEventCollapseOrExpand {

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventCollapse(UiFeatureCollapse source, boolean programmatic) {

    super(source, programmatic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.COLLAPSE;
  }

}
