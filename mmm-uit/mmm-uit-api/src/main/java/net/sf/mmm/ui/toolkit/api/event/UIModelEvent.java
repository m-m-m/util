/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.event;

import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the abstract base class of all events sent from models of the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UIModelEvent { // implements ChangeEvent {

  /** the event type */
  private ChangeType type;

  /**
   * The constructor.
   * 
   * @param eventType is the type for the new event.
   */
  protected UIModelEvent(ChangeType eventType) {

    super();
    this.type = eventType;
  }

  /**
   * {@inheritDoc}
   */
  public ChangeType getType() {

    return this.type;
  }

}
