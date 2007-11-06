/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.value.ValueParseStringException;

/**
 * This is the abstract base implementation of the {@link SmartIdManager}
 * interface.<br>
 * The only method that is left to be implemented is
 * {@link #createUniqueId(int)} because there may be various different
 * strategies.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSmartIdManager implements SmartIdManager {

  /**
   * The constructor.
   */
  public AbstractSmartIdManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getClassClassId() {

    return ClassId.CLASS;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getFieldClassId() {

    return ClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getRootClassId() {

    return ClassId.ROOT;
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getClassId(int classId) {

    return ClassId.valueOf(classId);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getFieldId(int fieldId) {

    return FieldId.valueOf(fieldId);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getId(String idAsString) {

    // TODO: improve this code!
    long resourceId = 0;
    int classId = 0;
    int revision = 0;
    int storeId = 0;

    StringBuffer token = new StringBuffer();
    int end = idAsString.length() - 1;
    int pos = 0;
    for (int i = 0; i <= end; i++) {
      char c = idAsString.charAt(i);
      if (i == end) {
        token.append(c);
        c = SmartId.SEPARATOR_CHAR;
      }
      if (c == SmartId.SEPARATOR_CHAR) {
        String s = token.toString();
        token.setLength(0);
        try {
          if (pos == 0) {
            resourceId = Long.parseLong(s, SmartId.RADIX);
          } else if (pos == 1) {
            classId = Integer.parseInt(s, SmartId.RADIX);
          } else if (pos == 2) {
            revision = Integer.parseInt(s, SmartId.RADIX);
          } else if (pos == 3) {
            storeId = Integer.parseInt(s, SmartId.RADIX);
          }
        } catch (NumberFormatException e) {
          throw new ValueParseStringException(idAsString, SmartId.class, ContentId.VALUE_NAME);
        }
        pos++;
      } else {
        token.append(c);
      }
    }
    if ((pos < 2) || (pos > 4)) {
      throw new ValueParseStringException(idAsString, SmartId.class, ContentId.VALUE_NAME);
    }
    return getId(resourceId, classId, revision, storeId);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getId(long objectId, int classId) {

    return getId(objectId, classId, 0, 0);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getId(long objectId, int classId, int revision) {

    return getId(objectId, classId, revision, 0);
  }

  /**
   * {@inheritDoc}
   */
  public SmartId getId(long objectId, int classId, int revision, int storeId) {

    if (objectId == SmartId.OID_CLASS) {
      return ClassId.valueOf(classId);
    } else if (objectId == SmartId.OID_FIELD) {
      return FieldId.valueOf(classId);
    } else if (storeId == 0) {
      if (revision == 0) {
        return new ObjectId(objectId, classId);
      } else {
        return new RevisionId(objectId, classId, revision);
      }
    } else {
      return new GenericId(objectId, classId, revision, storeId);
    }
  }

}
