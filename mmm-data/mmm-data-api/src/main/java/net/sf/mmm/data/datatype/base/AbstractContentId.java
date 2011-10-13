/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.datatype.base;

import net.sf.mmm.data.datatype.api.ContentId;

/**
 * This is the abstract base implementation of the {@link ContentId} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentId implements ContentId {

  /** UID for serialization. */
  private static final long serialVersionUID = 3656414714052544118L;

  /** @see #getObjectId() */
  private final long objectId;

  /**
   * The constructor.
   * 
   * @param objectId is the {@link #getObjectId() object-ID}.
   */
  public AbstractContentId(long objectId) {

    super();
    this.objectId = objectId;
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
  public ContentId getValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AbstractContentId)) {
      return false;
    }
    AbstractContentId id = (AbstractContentId) obj;
    if (this.objectId != id.objectId) {
      return false;
    }
    if (getClassId() != id.getClassId()) {
      return false;
    }
    if (getRevision() != id.getRevision()) {
      return false;
    }
    if (getStoreId() != id.getStoreId()) {
      // illegal case?
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    int hash = (int) this.objectId;
    hash = (hash << 8);
    hash = hash ^ getClassId();
    hash = hash + getRevision() + getStoreId();
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(Long.toString(this.objectId, RADIX));
    sb.append('.');
    sb.append(Integer.toString(getClassId(), RADIX));
    int revision = getRevision();
    if (revision != 0) {
      sb.append('.');
      sb.append(Integer.toString(revision, RADIX));
    }
    int store = getStoreId();
    if (store != 0) {
      sb.append(':');
      sb.append(Integer.toString(store, RADIX));
    }
    return sb.toString();
  }
}
