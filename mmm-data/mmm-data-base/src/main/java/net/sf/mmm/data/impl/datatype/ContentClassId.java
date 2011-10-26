/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataReflectionObject;
import net.sf.mmm.data.base.datatype.AbstractContentId;

/**
 * This is the implementation of the {@link DataId} interface for the ID of a
 * {@link net.sf.mmm.data.api.reflection.DataClass ContentClass}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class ContentClassId extends AbstractContentId {

  /** UID for serialization. */
  private static final long serialVersionUID = -7638229489656133262L;

  /** @see #POOL */
  private static final int POOL_SIZE = 256;

  /** @see #valueOf(int) */
  private static final ContentClassId[] POOL = new ContentClassId[POOL_SIZE];

  /**
   * the id of the root {@link net.sf.mmm.data.api.reflection.DataClass
   * ContentClass} (the class that all other classes are derived from).
   */
  public static final ContentClassId ROOT = valueOf(DataObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.api.reflection.DataReflectionObject
   * content-reflection-object}.
   */
  public static final ContentClassId REFELCTION = valueOf(DataReflectionObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects itself (Like {@link Class} in java).
   */
  public static final ContentClassId CLASS = valueOf(DataClass.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.api.reflection.DataClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.api.reflection.DataField content-field}.
   */
  public static final ContentClassId FIELD = valueOf(DataField.CLASS_ID);

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.DataIdManager} to create
   * instances.
   * 
   * @param objectId is the {@link #getObjectId() object-ID} identifying the
   *        {@link DataClass}.
   */
  public ContentClassId(int objectId) {

    super(objectId);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getContentClassId() {

    return CLASS;
  }

  /**
   * {@inheritDoc}
   */
  public int getClassId() {

    return DataClass.CLASS_ID;
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
   * This method gets the {@link ContentClassId} instance for the given
   * <code>classUid</code>. A pool is used to store the ID instances for the
   * first N <code>class-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param classUid is the {@link #getObjectId() object-ID} of the requested
   *        {@link ContentClassId} instance.
   * @return the requested {@link ContentClassId} instance.
   */
  public static ContentClassId valueOf(int classUid) {

    ContentClassId id;
    if (classUid < POOL_SIZE) {
      id = POOL[classUid];
      if (id == null) {
        id = new ContentClassId(classUid);
        POOL[classUid] = id;
      }
    } else {
      id = new ContentClassId(classUid);
    }
    return id;
  }

}
