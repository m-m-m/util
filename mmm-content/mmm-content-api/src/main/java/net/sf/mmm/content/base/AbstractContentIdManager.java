/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.base;

import net.sf.mmm.content.api.ContentIdManager;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This is the abstract base implementation of the {@link ContentIdManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractContentIdManager implements ContentIdManager {

  /**
   * The constructor.
   */
  public AbstractContentIdManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getRootClassId() {

    return getClassId(ContentObject.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getClassClassId() {

    return getClassId(ContentClass.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getFieldClassId() {

    return getClassId(ContentField.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getClassId(int classId) {

    return getId(classId, ContentClass.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getFieldId(int fieldId) {

    return getId(fieldId, ContentField.CLASS_ID);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getId(String idAsString) {

    long resourceId = 0;
    int classId = 0;
    int revision = 0;
    int storeId = 0;

    int end = idAsString.length() - 1;
    int tokenStart = 0;
    int tokenCount = 0;
    for (int i = 0; i <= end; i++) {
      char c = idAsString.charAt(i);
      if ((c == ContentId.SEPARATOR_CHAR) || (i == end)) {
        int tokenEnd = i;
        if (i == end) {
          tokenEnd++;
        }
        String token = idAsString.substring(tokenStart, tokenEnd);
        try {
          if (tokenCount == 0) {
            resourceId = Long.parseLong(token, ContentId.RADIX);
          } else if (tokenCount == 1) {
            classId = Integer.parseInt(token, ContentId.RADIX);
          } else if (tokenCount == 2) {
            revision = Integer.parseInt(token, ContentId.RADIX);
          } else if (tokenCount == 3) {
            storeId = Integer.parseInt(token, ContentId.RADIX);
          }
        } catch (NumberFormatException e) {
          throw new NlsParseException(e, idAsString, ContentId.class);
        }
        tokenCount++;
      }
    }
    if ((tokenCount < 2) || (tokenCount > 4)) {
      throw new NlsParseException(idAsString, ContentId.class);
    }
    return getId(resourceId, classId, revision, storeId);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getId(long objectId, int classId) {

    return getId(objectId, classId, 0, 0);
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getId(long objectId, int classId, int revision) {

    return getId(objectId, classId, revision, 0);
  }
}
