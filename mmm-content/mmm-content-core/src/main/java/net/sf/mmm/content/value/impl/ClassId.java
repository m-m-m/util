/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentReflectionObject;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the {@link SmartId} interface for the ID of a
 * {@link net.sf.mmm.content.model.api.ContentClass ContentClass}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ClassId implements SmartId {

  /** UID for serialization. */
  private static final long serialVersionUID = -7638229489656133262L;

  /** @see #valueOf(int) */
  private static final Map<Integer, ClassId> POOL = new HashMap<Integer, ClassId>(64);

  /** @see #toString() */
  private static final String PREFIX = Long.toString(OID_CLASS, 16) + SEPARATOR;

  /**
   * the id of the root
   * {@link net.sf.mmm.content.model.api.ContentClass ContentClass} (the class
   * that all other classes are derived from).
   */
  public static final ClassId ROOT = valueOf(ContentObject.CLASS_ID);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects
   * {@link net.sf.mmm.content.model.api.ContentReflectionObject content-reflection-object}.
   */
  public static final ClassId REFELCTION = valueOf(ContentReflectionObject.CLASS_ID);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects itself (Like {@link Class} in java).
   */
  public static final ClassId CLASS = valueOf(ContentClass.CLASS_ID);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects {@link net.sf.mmm.content.model.api.ContentField content-field}.
   */
  public static final ClassId FIELD = valueOf(ContentField.CLASS_ID);

  /** @see #getClassId() */
  private final int classId;

  /**
   * The constructor.
   * 
   * @param classId is the {@link #getClassId() class-ID}.
   */
  private ClassId(int classId) {

    super();
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

    return CLASS;
  }

  /**
   * {@inheritDoc}
   */
  public long getObjectId() {

    return OID_CLASS;
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

    return this.classId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if ((obj != null) && (obj instanceof ClassId)) {
      ClassId other = (ClassId) obj;
      return (this.classId == other.classId);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return PREFIX + Integer.toString(this.classId, 16);
  }

  /**
   * This method gets the {@link ClassId} instance for the given
   * <code>classUid</code>. A pool is used to store the ID instances for the
   * first N <code>class-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param classUid is the {@link #getClassId() class-ID} of the requested ID
   *        instance.
   * @return the requested {@link ClassId} instance.
   */
  public static ClassId valueOf(int classUid) {

    ClassId id;
    if (classUid < 256) {
      Integer key = Integer.valueOf(classUid);
      id = POOL.get(key);
      if (id == null) {
        id = new ClassId(classUid);
        POOL.put(key, id);
      }
    } else {
      id = new ClassId(classUid);
    }
    return id;
  }

}
