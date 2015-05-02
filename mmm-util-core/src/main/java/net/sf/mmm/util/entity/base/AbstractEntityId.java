/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.base;

import net.sf.mmm.util.entity.api.EntityId;

/**
 * This is the abstract base implementation of {@link EntityId}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractEntityId implements EntityId {

  /** UID for serialization. */
  private static final long serialVersionUID = 3656414714052544118L;

  /**
   * The constructor.
   */
  public AbstractEntityId() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return toString();
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
    if (!(obj instanceof AbstractEntityId)) {
      return false;
    }
    AbstractEntityId id = (AbstractEntityId) obj;
    if (getObjectId() != id.getObjectId()) {
      return false;
    }
    if (getTypeId() != id.getTypeId()) {
      return false;
    }
    if (getRevision() != id.getRevision()) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    int hash = (int) getObjectId();
    hash = (hash << 8);
    hash = hash ^ ((int) getTypeId());
    Number revision = getRevision();
    if (revision != null) {
      hash = hash + revision.intValue();
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(Long.toString(getObjectId(), RADIX));
    sb.append('.');
    sb.append(Long.toString(getTypeId(), RADIX));
    Number revision = getRevision();
    if (revision != null) {
      sb.append('.');
      sb.append(Long.toString(revision.longValue(), RADIX));
    }
    return sb.toString();
  }

}
