/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.base.AbstractContentClass;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.model.base.AbstractContentModelServiceBase;
import net.sf.mmm.content.model.base.ContentClassLoaderStAX;
import net.sf.mmm.content.model.base.ContentClassLoader;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.impl.SmartIdManagerImpl;
import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.util.resource.DataResource;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.content.model.api.ContentModelService} interface that
 * assumes that {@link SmartId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicContentModelService extends AbstractContentModelServiceBase {

  /** The default class loader. */
  private ContentClassLoader classLoader;

  /**
   * The constructor.
   */
  public BasicContentModelService() {

    super();
  }

  /**
   * @return the classLoader
   */
  public ContentClassLoader getClassLoader() {

    return this.classLoader;
  }

  /**
   * @param classLoader the classLoader to set
   */
  public void setClassLoader(ContentClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() throws IOException {

    if (getIdManager() == null) {
      setIdManager(new SmartIdManagerImpl());
    }
    if (this.classLoader == null) {
      this.classLoader = new ContentClassLoaderStAX(this);
    }
    DataResource resource = getModelResource();
    this.classLoader.loadClasses(resource);
  }

  protected DataResource getModelResource() {

    return new ClasspathResource(XML_MODEL_LOCATION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addClass(ContentClass contentClass) throws ContentModelException {

    super.addClass(contentClass);
    ContentId id = contentClass.getId();
    if (getIdManager().getClassClassId().equals(id)) {
      ContentClassImpl.setContentClass(contentClass);
    } else if (getIdManager().getFieldClassId().equals(id)) {
      ContentFieldImpl.setContentClass(contentClass);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractContentClass newClass() {

    return new ContentClassImpl();
  }

  /**
   * {@inheritDoc}
   */
  protected AbstractContentField newField() {

    return new ContentFieldImpl();
  }

}
