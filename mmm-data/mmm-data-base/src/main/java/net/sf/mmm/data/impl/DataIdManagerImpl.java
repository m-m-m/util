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
import net.sf.mmm.data.impl.datatype.DataClassId;
import net.sf.mmm.data.impl.datatype.DataFieldId;
import net.sf.mmm.data.impl.datatype.DataObjectGenericId;
import net.sf.mmm.data.impl.datatype.DataObjectId;

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

    return DataClassId.CLASS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getFieldClassId() {

    return DataClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getRootClassId() {

    return DataClassId.ROOT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getClassId(long classId) {

    return DataClassId.valueOf(classId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataId getFieldId(long fieldId) {

    return DataFieldId.valueOf(fieldId);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getId(long objectId, long classId, int revision, int storeId) {

    if (classId == DataClass.CLASS_ID) {
      return DataClassId.valueOf((int) objectId);
    } else if (classId == DataField.CLASS_ID) {
      return DataFieldId.valueOf((int) objectId);
    } else if ((storeId == 0) && (revision == 0)) {
      return new DataObjectId(objectId, classId);
    } else {
      return new DataObjectGenericId(objectId, classId, revision, storeId);
    }
  }

}
