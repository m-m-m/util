/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.datatype.impl;

import net.sf.mmm.data.api.ContentObject;
import net.sf.mmm.data.datatype.api.ContentId;
import net.sf.mmm.data.datatype.base.AbstractContentId;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.reflection.api.ContentField;
import net.sf.mmm.data.reflection.api.ContentReflectionObject;

/**
 * This is the implementation of the {@link ContentId} interface for the ID of a
 * {@link net.sf.mmm.data.reflection.api.ContentClass ContentClass}.
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
   * the id of the root {@link net.sf.mmm.data.reflection.api.ContentClass
   * ContentClass} (the class that all other classes are derived from).
   */
  public static final ContentClassId ROOT = valueOf(ContentObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.reflection.api.ContentReflectionObject
   * content-reflection-object}.
   */
  public static final ContentClassId REFELCTION = valueOf(ContentReflectionObject.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} that reflects itself (Like {@link Class} in java).
   */
  public static final ContentClassId CLASS = valueOf(ContentClass.CLASS_ID);

  /**
   * the id of the {@link net.sf.mmm.data.reflection.api.ContentClass
   * content-class} that reflects
   * {@link net.sf.mmm.data.reflection.api.ContentField content-field}.
   */
  public static final ContentClassId FIELD = valueOf(ContentField.CLASS_ID);

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.ContentIdManager} to create
   * instances.
   * 
   * @param objectId is the {@link #getObjectId() object-ID} identifying the
   *        {@link ContentClass}.
   */
  public ContentClassId(int objectId) {

    super(objectId);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getContentClassId() {

    return CLASS;
  }

  /**
   * {@inheritDoc}
   */
  public int getClassId() {

    return ContentClass.CLASS_ID;
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
