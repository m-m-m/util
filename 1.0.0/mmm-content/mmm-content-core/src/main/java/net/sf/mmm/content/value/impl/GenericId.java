/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

/**
 * This is the generic implementation of the
 * {@link net.sf.mmm.content.value.base.SmartId SmartId} interface. It is only
 * used in complex situations where the repository combines multiple resource
 * stores.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericId extends RevisionId {

  /** UID for serialization. */
  private static final long serialVersionUID = -4101647956836789180L;

  /** @see #getStoreId() */
  private final int storeId;

  /**
   * The constructor.
   * 
   * @param objectId
   * @param classId
   * @param revision
   * @param storeId
   */
  public GenericId(long objectId, int classId, int revision, int storeId) {

    super(objectId, classId, revision);
    this.storeId = storeId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getStoreId() {

    return this.storeId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toString(StringBuffer buffer) {

    super.toString(buffer);
    buffer.append(':');
    buffer.append(Integer.toString(this.storeId, RADIX));
  }

}
