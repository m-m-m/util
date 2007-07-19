/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.persistence.base;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.persistence.api.IdService;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.value.api.ValueParseException;

/**
 * This is the abstract base implementation of the {@link IdService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractIdService implements IdService {

  /**
   * {@inheritDoc}
   */
  public ContentId createId(ContentClass type) {

    if (type.getModifiers().isAbstract()) {
      throw new IllegalArgumentException("Class is abstract!");
    }
    ContentId classId = type.getId();
    if (classId == IdImpl.ID_CLASS_CLASS) {
      // create class ID
      return createClassId();
    } else if (classId == IdImpl.ID_CLASS_FIELD) {
      // create field ID
      return createFieldId();
      // } else if (classId == IdImpl.ID_CLASS_USER) {
      // create user ID
      // return createUserId();
      // } else if (classId == ...) {
    } else {
      // create resource ID
    }
    return null;
  }

  protected abstract ContentId createClassId();

  protected abstract ContentId createFieldId();

  // protected abstract Id createUserId();

  protected abstract ContentId createResourceId(ContentClass type);

  /**
   * {@inheritDoc}
   */
  public ContentId getContentClassId(ContentId id) {

    IdImpl idImpl = (IdImpl) id;
    return new IdImpl(idImpl.getClassId());
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getContentFolderId() {

    return IdImpl.ID_FOLDER_RESOURCES;
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getRootClassId() {

    return IdImpl.ID_CLASS_ROOT;
  }

  /**
   * {@inheritDoc}
   */
  public ContentId getRootFolderId() {

    return IdImpl.ID_FOLDER_ROOT;
  }

  /**
   * {@inheritDoc}
   */
  public void releaseId(ContentId unusedId) {

    IdImpl idImpl = (IdImpl) unusedId;

  }

  /**
   * {@inheritDoc}
   */
  public ContentId toId(String id) throws ValueParseException {

    return new IdImpl(id);
  }

}
