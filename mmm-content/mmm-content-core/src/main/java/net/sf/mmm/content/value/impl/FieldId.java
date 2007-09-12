/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the {@link SmartId} interface for the ID of a
 * {@link net.sf.mmm.content.model.api.ContentField ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class FieldId implements SmartId {

  /** UID for serialization. */
  private static final long serialVersionUID = 2291237026928351257L;

  /** @see #POOL */
  private static final int POOL_SIZE = 256;

  /** @see #valueOf(int) */
  private static final FieldId[] POOL = new FieldId[POOL_SIZE];

  /** @see #toString() */
  private static final String PREFIX = Long.toString(OID_FIELD, RADIX) + SEPARATOR;

  /**
   * the id of the {@link net.sf.mmm.content.model.api.ContentField field}
   * <code>id</code>.
   */
  public static final FieldId ID = valueOf(0);

  /** @see #getClassId() */
  private final int fieldId;

  /**
   * The constructor.
   * 
   * @param fieldUid is the {@link #getClassId() class-ID}.
   */
  public FieldId(int fieldUid) {

    super();
    this.fieldId = fieldUid;
  }

  /**
   * {@inheritDoc}
   */
  public int getClassId() {

    return this.fieldId;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getContentClassId() {

    return ClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  public long getObjectId() {

    return OID_FIELD;
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
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return -this.fieldId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj != null) && (obj instanceof FieldId)) {
      FieldId other = (FieldId) obj;
      return (this.fieldId == other.fieldId);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return PREFIX + Integer.toString(this.fieldId, RADIX);
  }

  /**
   * This method gets the {@link FieldId} instance for the given
   * <code>fieldUid</code>. A pool is used to store the ID instances for the
   * first N <code>field-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param fieldUid is the {@link #getClassId() class-ID} of the requested ID
   *        instance.
   * @return the requested {@link ClassId} instance.
   */
  public static FieldId valueOf(int fieldUid) {

    FieldId id;
    if (fieldUid < POOL_SIZE) {
      id = POOL[fieldUid];
      if (id == null) {
        id = new FieldId(fieldUid);
        POOL[fieldUid] = id;
      }
    } else {
      id = new FieldId(fieldUid);
    }
    return id;
  }

}
