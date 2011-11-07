/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.util.event.api.ChangeEvent;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This class represents an event that is sent to notify about changes of the
 * content model. A change informs about a class or field that has been added,
 * removed or updated.
 * 
 * @param <CLASS> is the generic type for the bound of
 *        {@link DataClass#getJavaClass()}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DataReflectionEvent<CLASS extends DataObject> implements ChangeEvent {

  /** the type of the event */
  private final ChangeType type;

  /** the data class this event is about */
  private final DataClass<CLASS> contentClass;

  /** the field that this event is about */
  private final DataField<CLASS, ?> contentField;

  /**
   * This is the constructor for a class event. The event will be about the
   * complete class and not about a specific field in the class.
   * 
   * @param changedClass is the class that has been added, removed, or updated.
   * @param changeType is the type of event.
   */
  public DataReflectionEvent(DataClass<CLASS> changedClass, ChangeType changeType) {

    super();
    this.type = changeType;
    this.contentClass = changedClass;
    this.contentField = null;
  }

  /**
   * This is the constructor for a field event. The event will only be about the
   * indexed field of the given class.
   * 
   * @param changedField is the field that has been added, removed, or updated.
   * @param changeType is the type of event (add/remove/update). Use only the
   *        static constant fields (TYPE_*)!
   */
  public DataReflectionEvent(DataField<CLASS, ?> changedField, ChangeType changeType) {

    super();
    this.type = changeType;
    this.contentField = changedField;
    this.contentClass = this.contentField.getDeclaringClass();
  }

  /**
   * This method gets the class this event is about. If this event notifies
   * about the change of a {@link #getContentField() field} this method gets the
   * {@link DataField#getDeclaringClass() declaring class} of that field.
   * 
   * @return the class the event is about.
   */
  public DataClass<CLASS> getContentClass() {

    return this.contentClass;
  }

  /**
   * This method gets the field this event is about.
   * 
   * @return the field the event is about or <code>null</code> if the event is
   *         about the class itself.
   */
  public DataField<CLASS, ?> getContentField() {

    return this.contentField;
  }

  /**
   * {@inheritDoc}
   */
  public ChangeType getType() {

    return this.type;
  }

}
