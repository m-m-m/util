/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the {@link SmartId} interface for the ID of the
 * latest version of a
 * {@link net.sf.mmm.content.api.ContentObject ContentObject} that is no
 * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} or
 * {@link net.sf.mmm.content.model.api.ContentField ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ObjectId implements SmartId {

  /** UID for serialization. */
  private static final long serialVersionUID = -2124272902771327589L;

  /** @see #getObjectId() */
  private final long objectId;

  /** @see #getClassId() */
  private final int classId;

  /**
   * The constructor.
   * 
   * @param objectId is the {@link #getObjectId() object-id}.
   * @param classId is the {@link #getClassId() class-id}.
   */
  ObjectId(long objectId, int classId) {

    super();
    this.objectId = objectId;
    this.classId = classId;
  }

  /**
   * {@inheritDoc}
   */
  public int getClassId() {

    return this.classId;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getContentClassId() {

    return ClassId.valueOf(this.classId);
  }

  /**
   * {@inheritDoc}
   */
  public long getObjectId() {

    return this.objectId;
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getStoreId() {

    return 0;
  }

  /**
   * This method implements the logic for {@link #toString()}.
   * 
   * @param buffer is the string buffer to use.
   */
  protected void toString(StringBuffer buffer) {

    buffer.append(Long.toString(this.objectId, RADIX));
    buffer.append(SEPARATOR_CHAR);
    buffer.append(Integer.toString(this.classId, RADIX));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer buffer = new StringBuffer();
    toString(buffer);
    return buffer.toString();
  }

}
