/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#COLLAPSE} or {@link EventType#EXPAND}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiEventCollapseOrExpand extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureCollapse source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventCollapseOrExpand(UiFeatureCollapse source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureCollapse getSource() {

    return this.source;
  }

}
