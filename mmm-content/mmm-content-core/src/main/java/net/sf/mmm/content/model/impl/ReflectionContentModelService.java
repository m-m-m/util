/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.base.NativeContentClassLoader;
import net.sf.mmm.content.model.base.NativeContentClassLoaderImpl;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionContentModelService extends BasicContentModelService {

  /** The "class-loader" used to read the custom content-model. */
  private NativeContentClassLoader nativeClassLoader;

  /**
   * The constructor.
   */
  public ReflectionContentModelService() {

    super();
  }

  /**
   * @param classLoader the class-loader to set
   */
  @Resource
  public void setNativeClassLoader(NativeContentClassLoader classLoader) {

    this.nativeClassLoader = classLoader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() throws IOException {

    super.initialize();
    if (this.nativeClassLoader == null) {
      this.nativeClassLoader = new NativeContentClassLoaderImpl(this);
    }
    
  }

  public void initialize(Set<Class<? extends ContentObject>> entityTypes) {

    this.nativeClassLoader.loadClasses(entityTypes);
  }
}
