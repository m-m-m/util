/* $Id$ */
package net.sf.mmm.content.model.api;

import net.sf.mmm.util.event.ChangeEvent;

/**
 * This class represents an event that is sent to notify about changes of the
 * content model. A change informs about a class or field that has beend added,
 * removed or updated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ContentModelEvent implements ChangeEvent {

  /** the type of the event */
  private final Type type;

  /** the data class this event is about */
  private final ContentClassIF contentClass;

  /** the field that this event is about */
  private final ContentFieldIF contentField;

  /**
   * This is the constructor for a class event. The event will be about the
   * complete class and not about a specific field in the class.
   * 
   * @param changedClass
   *        is the class that has been added, removed, or updated.
   * @param changeType
   *        is the type of event.
   */
  public ContentModelEvent(ContentClassIF changedClass, Type changeType) {

    super();
    this.type = changeType;
    this.contentClass = changedClass;
    this.contentField = null;
  }

  /**
   * This is the constructor for a field event. The event will only be about the
   * indexed field of the given class.
   * 
   * @param changedField
   *        is the field that has been added, removed, or updated.
   * @param changeType
   *        is the type of event (add/remove/update). Use only the static
   *        constant fields (TYPE_*)!
   */
  public ContentModelEvent(ContentFieldIF changedField, Type changeType) {

    super();
    this.type = changeType;
    this.contentField = changedField;
    this.contentClass = this.contentField.getContentClass();
  }

  /**
   * This method gets the class this event is about.
   * 
   * @return the class the event is about.
   */
  public ContentClassIF getContentClass() {

    return this.contentClass;
  }

  /**
   * This method gets the field this event is about.
   * 
   * @return the field the event is about or <code>null</code> if the event is
   *         about the class itself.
   */
  public ContentFieldIF getContentField() {

    return this.contentField;
  }

  /**
   * @see net.sf.mmm.util.event.ChangeEvent#getType()
   */
  public Type getType() {

    return this.type;
  }

}
