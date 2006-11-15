/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

import net.sf.mmm.util.event.ChangeEvent;

/**
 * This is the abstract base class of all events sent from models of the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIModelEvent implements ChangeEvent {

  /** the event type */
  private Type type;

  /**
   * The constructor.
   * 
   * @param eventType
   *        is the type for the new event.
   */
  protected UIModelEvent(Type eventType) {

    super();
    this.type = eventType;
  }

  /**
   * @see net.sf.mmm.util.event.ChangeEvent#getType()
   */
  public Type getType() {

    return this.type;
  }

}
