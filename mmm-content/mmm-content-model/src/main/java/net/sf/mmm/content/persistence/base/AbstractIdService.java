/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.persistence.base;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.persistence.api.IdService;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;
import net.sf.mmm.value.api.ValueParseException;

/**
 * This is the abstract base implementation of the {@link IdService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractIdService implements IdService {

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#createId(net.sf.mmm.content.model.api.ContentClass)
   */
  public Id createId(ContentClass type) {

    if (type.getModifiers().isAbstract()) {
      throw new IllegalArgumentException("Class is abstract!");
    }
    Id classId = type.getId();
    if (classId == IdImpl.ID_CLASS_CLASS) {
      // create class ID
      return createClassId();
    } else if (classId == IdImpl.ID_CLASS_FIELD) {
      // create field ID
      return createFieldId();
    //} else if (classId == IdImpl.ID_CLASS_USER) {
      // create user ID
      //return createUserId();
    //} else if (classId == ...) {
    } else {
      // create resource ID
    }
    return null;
  }

  protected abstract Id createClassId();
  
  protected abstract Id createFieldId();
  
  //protected abstract Id createUserId();

  protected abstract Id createResourceId(ContentClass type);
  
  /**
   * @see net.sf.mmm.content.persistence.api.IdService#getContentClassId(net.sf.mmm.content.value.api.Id)
   */
  public Id getContentClassId(Id id) {

    IdImpl idImpl = (IdImpl) id;
    return new IdImpl(idImpl.getClassId());
  }

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#getContentFolderId()
   */
  public Id getContentFolderId() {

    return IdImpl.ID_FOLDER_RESOURCES;
  }

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#getRootClassId()
   */
  public Id getRootClassId() {

    return IdImpl.ID_CLASS_ROOT;
  }

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#getRootFolderId()
   */
  public Id getRootFolderId() {

    return IdImpl.ID_FOLDER_ROOT;
  }

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#releaseId(net.sf.mmm.content.value.api.Id)
   */
  public void releaseId(Id unusedId) {

    IdImpl idImpl = (IdImpl) unusedId;
    
  }

  /**
   * @see net.sf.mmm.content.persistence.api.IdService#toId(java.lang.String)
   */
  public Id toId(String id) throws ValueParseException {

    return new IdImpl(id);
  }

}
