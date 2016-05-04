/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.entity.base;

import net.sf.mmm.util.entity.api.EntityId;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsParseException;

/**
 * This is the implementation of {@link EntityId} including identification of {@link #getRevision() revision}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class EntityIdImpl extends AbstractEntityId {

  private static final long serialVersionUID = 1553359087706129686L;

  private long objectId;

  private long typeId;

  private Number revision;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected EntityIdImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param objectId - see {@link #getObjectId()}.
   * @param typeId - see {@link #getTypeId()}.
   * @param revision - see {@link #getRevision()}.
   */
  public EntityIdImpl(long objectId, long typeId, Number revision) {

    super();
    if (objectId == OBJECT_ID_ILLEGAL) {
      throw new NlsIllegalArgumentException(Long.valueOf(objectId), "ID");
    }
    this.objectId = objectId;
    this.typeId = typeId;
    this.revision = revision;
  }

  /**
   * The constructor.
   *
   * @param entityId is the {@link EntityId} instance to "clone".
   */
  public EntityIdImpl(EntityId entityId) {

    this(entityId.getObjectId(), entityId.getTypeId(), entityId.getRevision());
  }

  /**
   * The constructor.
   *
   * @param idAsString is the {@link #toString() string representation} of the {@link EntityId} to create.
   */
  public EntityIdImpl(String idAsString) {

    super();
    int end = idAsString.length() - 1;
    int tokenStart = 0;
    int tokenCount = 0;
    for (int i = 0; i <= end; i++) {
      char c = idAsString.charAt(i);
      if ((c == SEPARATOR_CHAR) || (i == end)) {
        int tokenEnd = i;
        if (i == end) {
          // no separator to omit at the end...
          tokenEnd++;
        }
        String token = idAsString.substring(tokenStart, tokenEnd);
        try {
          if (tokenCount == 0) {
            this.objectId = Long.parseLong(token, RADIX);
          } else if (tokenCount == 1) {
            this.typeId = Integer.parseInt(token, RADIX);
          } else if (tokenCount == 2) {
            this.revision = Integer.valueOf(Integer.parseInt(token, RADIX));
          }
        } catch (NumberFormatException e) {
          throw new NlsParseException(e, idAsString, EntityId.class);
        }
        tokenCount++;
      }
    }
    if ((tokenCount < 2) || (tokenCount > 3)) {
      throw new NlsParseException(idAsString, EntityId.class);
    }
  }

  @Override
  public Number getRevision() {

    return this.revision;
  }

  @Override
  public long getTypeId() {

    return this.typeId;
  }

  @Override
  public long getObjectId() {

    return this.objectId;
  }

}
