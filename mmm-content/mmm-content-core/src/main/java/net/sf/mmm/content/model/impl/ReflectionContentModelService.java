/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.util.Set;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentObject;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionContentModelService extends BasicContentModelService {

  /** The "class-loader" used to read the custom content-model. */
  private ContentModelClassReader classReader;

  /**
   * The constructor.
   */
  public ReflectionContentModelService() {

    super();
  }

  /**
   * @param classReader the classReader to set
   */
  @Resource
  public void setClassReader(ContentModelClassReader classReader) {

    this.classReader = classReader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {

    super.initialize();
    if (this.classReader == null) {
      ContentModelClassReader reader = new ContentModelClassReader();
      reader.initialize();
      this.classReader = reader;
    }
  }

  public void initialize(Set<Class<? extends ContentObject>> entityTypes) {

    this.classReader.readClasses(entityTypes, this);
  }
}
