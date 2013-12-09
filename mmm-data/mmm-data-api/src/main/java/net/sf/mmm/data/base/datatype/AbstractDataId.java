/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.datatype;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the abstract base implementation of the {@link DataId} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataId implements DataId {

  /** UID for serialization. */
  private static final long serialVersionUID = 3656414714052544118L;

  /** @see #getObjectId() */
  private final long objectId;

  /**
   * The constructor.
   * 
   * @param objectId is the {@link #getObjectId() object-ID}.
   */
  public AbstractDataId(long objectId) {

    super();
    if (objectId == OBJECT_ID_ILLEGAL) {
      throw new NlsIllegalArgumentException(Long.valueOf(objectId), "ID");
    }
    this.objectId = objectId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return getTitle();
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
  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AbstractDataId)) {
      return false;
    }
    AbstractDataId id = (AbstractDataId) obj;
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
    int classId = (int) getClassId();
    hash = hash ^ classId;
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
    sb.append(Long.toString(getClassId(), RADIX));
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
