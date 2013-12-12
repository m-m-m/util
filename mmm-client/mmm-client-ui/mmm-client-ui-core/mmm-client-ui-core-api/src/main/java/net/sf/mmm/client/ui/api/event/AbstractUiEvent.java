/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

/**
 * This is the abstract base implementation of {@link UiEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiEvent implements UiEvent {

  /** @see #isProgrammatic() */
  private final boolean programmatic;

  /**
   * The constructor.
   * 
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public AbstractUiEvent(boolean programmatic) {

    super();
    this.programmatic = programmatic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isProgrammatic() {

    return this.programmatic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName() + "[" + getType() + "@" + getSource()
        + (this.programmatic ? ",programmatic]" : "]");
  }

}
