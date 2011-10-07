/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.base.AbstractContentIdManager;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.datatype.impl.ContentClassId;
import net.sf.mmm.content.datatype.impl.ContentFieldId;
import net.sf.mmm.content.datatype.impl.ContentObjectGenericId;
import net.sf.mmm.content.datatype.impl.ContentObjectId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentField;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.api.ContentIdManager} interface.
 * 
 * TODO: Move to implementation module?
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class ContentIdManagerImpl extends AbstractContentIdManager {

  /**
   * The constructor.
   * 
   */
  public ContentIdManagerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentId getClassClassId() {

    return ContentClassId.CLASS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentId getFieldClassId() {

    return ContentClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentId getRootClassId() {

    return ContentClassId.ROOT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentId getClassId(int classId) {

    return ContentClassId.valueOf(classId);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContentId getFieldId(int fieldId) {

    return ContentFieldId.valueOf(fieldId);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getId(long objectId, int classId, int revision, int storeId) {

    if (classId == ContentClass.CLASS_ID) {
      return ContentClassId.valueOf((int) objectId);
    } else if (classId == ContentField.CLASS_ID) {
      return ContentFieldId.valueOf((int) objectId);
    } else if ((storeId == 0) && (revision == 0)) {
      return new ContentObjectId(objectId, classId);
    } else {
      return new ContentObjectGenericId(objectId, classId, revision, storeId);
    }
  }

}
