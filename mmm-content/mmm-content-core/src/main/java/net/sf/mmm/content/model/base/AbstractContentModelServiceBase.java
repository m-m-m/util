/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import javax.annotation.Resource;

import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.reflect.ClassResolver;

/**
 * This an the abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService ContentModelService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentModelServiceBase extends AbstractContentModelService {

  /** @see #getIdManager() */
  private SmartIdManager idManager;
  
  /** @see #getClassResolver() */
  private ClassResolver classResolver;
  
  /**
   * The constructor.
   */
  public AbstractContentModelServiceBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SmartIdManager getIdManager() {

    return this.idManager;
  }

  /**
   * @param idManager the idManager to set
   */
  @Resource
  public void setIdManager(SmartIdManager idManager) {

    this.idManager = idManager;
  }

  /**
   * @return the classResolver
   */
  public ClassResolver getClassResolver() {

    return this.classResolver;
  }
  
  /**
   * @param classResolver the classResolver to set
   */
  @Resource
  public void setClassResolver(ClassResolver classResolver) {

    this.classResolver = classResolver;
  }
  
  /**
   * This method creates a new un-initialized content-class instance.
   * 
   * @return the new class.
   */
  protected abstract AbstractContentClass newClass();

  /**
   * This method creates a new un-initialized content-field instance.
   * 
   * @return the new field.
   */
  protected abstract AbstractContentField newField();

}
