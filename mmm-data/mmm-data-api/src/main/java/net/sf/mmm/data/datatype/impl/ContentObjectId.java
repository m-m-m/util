/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.datatype.impl;

import net.sf.mmm.data.datatype.api.ContentId;
import net.sf.mmm.data.datatype.base.AbstractContentId;
import net.sf.mmm.data.reflection.api.ContentClass;
import net.sf.mmm.data.reflection.api.ContentField;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of the {@link ContentId} interface for the ID of
 * the latest version of a {@link net.sf.mmm.data.api.ContentObject
 * ContentObject} that is no {@link net.sf.mmm.data.reflection.api.ContentClass}
 * or {@link net.sf.mmm.data.reflection.api.ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentObjectId extends AbstractContentId {

  /** UID for serialization. */
  private static final long serialVersionUID = -2124272902771327589L;

  /** @see #getClassId() */
  private final int classId;

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.ContentIdManager} to create
   * instances.
   * 
   * @param objectId is the {@link #getObjectId() object-id}.
   * @param classId is the {@link #getClassId() class-id}.
   */
  public ContentObjectId(long objectId, int classId) {

    super(objectId);
    if ((classId == ContentClass.CLASS_ID) || (classId == ContentField.CLASS_ID)) {
      throw new NlsIllegalArgumentException(Integer.valueOf(classId), "class-ID");
    }
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
  public ContentId getContentClassId() {

    return ContentClassId.valueOf(this.classId);
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

}
