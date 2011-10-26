/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base;

import net.sf.mmm.data.api.DataIdManager;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This is the abstract base implementation of the {@link DataIdManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDataIdManager implements DataIdManager {

  /**
   * The constructor.
   */
  public AbstractDataIdManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public DataId getRootClassId() {

    return getClassId(DataObject.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getClassClassId() {

    return getClassId(DataClass.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getFieldClassId() {

    return getClassId(DataField.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getClassId(int classId) {

    return getId(classId, DataClass.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getFieldId(int fieldId) {

    return getId(fieldId, DataField.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getId(String idAsString) {

    long resourceId = 0;
    int classId = 0;
    int revision = 0;
    int storeId = 0;

    int end = idAsString.length() - 1;
    int tokenStart = 0;
    int tokenCount = 0;
    for (int i = 0; i <= end; i++) {
      char c = idAsString.charAt(i);
      if ((c == DataId.SEPARATOR_CHAR) || (i == end)) {
        int tokenEnd = i;
        if (i == end) {
          tokenEnd++;
        }
        String token = idAsString.substring(tokenStart, tokenEnd);
        try {
          if (tokenCount == 0) {
            resourceId = Long.parseLong(token, DataId.RADIX);
          } else if (tokenCount == 1) {
            classId = Integer.parseInt(token, DataId.RADIX);
          } else if (tokenCount == 2) {
            revision = Integer.parseInt(token, DataId.RADIX);
          } else if (tokenCount == 3) {
            storeId = Integer.parseInt(token, DataId.RADIX);
          }
        } catch (NumberFormatException e) {
          throw new NlsParseException(e, idAsString, DataId.class);
        }
        tokenCount++;
      }
    }
    if ((tokenCount < 2) || (tokenCount > 4)) {
      throw new NlsParseException(idAsString, DataId.class);
    }
    return getId(resourceId, classId, revision, storeId);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getId(long objectId, int classId) {

    return getId(objectId, classId, 0, 0);
  }

  /**
   * {@inheritDoc}
   */
  public DataId getId(long objectId, int classId, int revision) {

    return getId(objectId, classId, revision, 0);
  }
}
