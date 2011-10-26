/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.persistence.impl.jpa.JpaRevisionedPersistenceEntity;

/**
 * This is the implementation of the abstract entity {@link DataObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractDataObject extends JpaRevisionedPersistenceEntity<Long> implements
    DataObject {

  /** UID for serialization. */
  private static final long serialVersionUID = 8616371370522165168L;

  /** @see #getTitle() */
  private String title;

  /**
   * The constructor.
   */
  public AbstractDataObject() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param title is the {@link #getTitle() title}.
   */
  public AbstractDataObject(String title) {

    super();
    if (title != null) {
      setTitle(title);
    }
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class ID}
   * identifying the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting
   * this object.
   * 
   * @return the {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class
   *         ID}.
   */
  public abstract int getDataClassId();

  /**
   * {@inheritDoc}
   */
  @Column(nullable = true)
  public String getTitle() {

    return this.title;
  }

  /**
   * This method sets the {@link #getTitle() name} of this object.<br>
   * <b>ATTENTION:</b><br>
   * This method should only be used internally. Especially this method can NOT
   * be used to rename this entity. Therefore you have to use the
   * {@link net.sf.mmm.data.api.repository.DataRepository}.
   * 
   * @param name the name to set
   */
  protected void setTitle(String name) {

    this.title = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String className = getClass().getSimpleName();
    String objectName = this.title;
    if (objectName == null) {
      objectName = "-";
    }
    int idLength = 0;
    Long id = getId();
    if (id != null) {
      idLength = 8;
    }
    StringBuffer buffer = new StringBuffer(className.length() + objectName.length() + idLength);
    buffer.append(className);
    buffer.append(':');
    buffer.append(objectName);
    if (id != null) {
      buffer.append(" [");
      buffer.append(id);
      buffer.append(']');
    }
    return buffer.toString();
  }

}
