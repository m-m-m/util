/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This is the abstract base class of all events sent from models of the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIModelEvent {

  /** the event type */
  private EventType type;

  /**
   * The constructor.
   * 
   * @param eventType
   *        is the type for the new event.
   */
  protected UIModelEvent(EventType eventType) {

    super();
    this.type = eventType;
  }

  /**
   * This method gets the type of event.
   * 
   * @return the event type (must be one of the TYPE_* constants).
   */
  public EventType getType() {

    return this.type;
  }

}
