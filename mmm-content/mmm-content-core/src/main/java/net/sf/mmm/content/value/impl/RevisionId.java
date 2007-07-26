/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.value.base.SmartId SmartId} interface for a
 * resource revision in the regular repository store.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class RevisionId extends ObjectId {

  /** UID for serialization. */
  private static final long serialVersionUID = 1553359087706129686L;

  /** @see #getRevision() */
  private final int revision;

  /**
   * The constructor.
   * 
   * @param objectId
   * @param classId
   * @param revision
   */
  RevisionId(long objectId, int classId, int revision) {

    super(objectId, classId);
    this.revision = revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toString(StringBuffer buffer) {

    super.toString(buffer);
    buffer.append(SEPARATOR_CHAR);
    buffer.append(Integer.toString(this.revision, RADIX));
  }

}
