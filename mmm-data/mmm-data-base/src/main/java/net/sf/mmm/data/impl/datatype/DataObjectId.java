/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.datatype.AbstractDataId;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of the {@link DataId} interface for the ID of
 * the latest version of a {@link net.sf.mmm.data.api.DataObject
 * ContentObject} that is no {@link net.sf.mmm.data.api.reflection.DataClass}
 * or {@link net.sf.mmm.data.api.reflection.DataField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataObjectId extends AbstractDataId {

  /** UID for serialization. */
  private static final long serialVersionUID = -2124272902771327589L;

  /** @see #getClassId() */
  private final int classId;

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.DataIdManager} to create
   * instances.
   * 
   * @param objectId is the {@link #getObjectId() object-id}.
   * @param classId is the {@link #getClassId() class-id}.
   */
  public DataObjectId(long objectId, int classId) {

    super(objectId);
    if ((classId == DataClass.CLASS_ID) || (classId == DataField.CLASS_ID)) {
      throw new NlsIllegalArgumentException(Integer.valueOf(classId), "class-ID");
    }
    this.classId = classId;
  }

  /**
   * {@inheritDoc}
   */
  public long getClassId() {

    return this.classId;
  }

  /**
   * {@inheritDoc}
   */
  public DataId getDataClassId() {

    return DataClassId.valueOf(this.classId);
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
