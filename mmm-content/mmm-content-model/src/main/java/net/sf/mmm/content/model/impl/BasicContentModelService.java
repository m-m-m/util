/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.base.AbstractContentModelService;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.content.value.impl.SmartIdManagerImpl;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService} interface that
 * assumes that {@link SmartId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicContentModelService extends AbstractContentModelService {

  /** @see #setIdManager(SmartIdManager) */
  private SmartIdManager idManager;

  /**
   * @param idManager the idManager to set
   */
  @Resource
  public void setIdManager(SmartIdManager idManager) {

    this.idManager = idManager;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (this.idManager == null) {
      this.idManager = new SmartIdManagerImpl();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addClass(ContentClass contentClass) throws ContentModelException {

    super.addClass(contentClass);
    ContentId id = contentClass.getId();
    if (this.idManager.getClassClassId().equals(id)) {
      ContentClassImpl.setContentClass(contentClass);
    } else if (this.idManager.getFieldClassId().equals(id)) {
      ContentFieldImpl.setContentClass(contentClass);
    }
  }

}
