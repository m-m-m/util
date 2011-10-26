/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.AbstractDataIdManager;
import net.sf.mmm.data.impl.datatype.ContentClassId;
import net.sf.mmm.data.impl.datatype.ContentFieldId;
import net.sf.mmm.data.impl.datatype.ContentObjectGenericId;
import net.sf.mmm.data.impl.datatype.ContentObjectId;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.data.api.DataIdManager} interface.
 * 
 * TODO: Move to implementation module?
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class DataIdManagerImpl extends AbstractDataIdManager {

  /**
   * The constructor.
   * 
   */
  public DataIdManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getClassClassId() {

    return ContentClassId.CLASS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getFieldClassId() {

    return ContentClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getRootClassId() {

    return ContentClassId.ROOT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getClassId(int classId) {

    return ContentClassId.valueOf(classId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getFieldId(int fieldId) {

    return ContentFieldId.valueOf(fieldId);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getId(long objectId, int classId, int revision, int storeId) {

    if (classId == DataClass.CLASS_ID) {
      return ContentClassId.valueOf((int) objectId);
    } else if (classId == DataField.CLASS_ID) {
      return ContentFieldId.valueOf((int) objectId);
    } else if ((storeId == 0) && (revision == 0)) {
      return new ContentObjectId(objectId, classId);
    } else {
      return new ContentObjectGenericId(objectId, classId, revision, storeId);
    }
  }

}
