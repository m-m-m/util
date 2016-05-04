/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is the implementation of the {@link PojoDescriptorDependencies} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (renamed, 1.1.0)
 */
@Singleton
public class PojoDescriptorDependenciesImpl extends AbstractComponent implements PojoDescriptorDependencies {

  private ReflectionUtil reflectionUtil;

  private CollectionReflectionUtil collectionReflectionUtil;

  /**
   * The constructor.
   */
  public PojoDescriptorDependenciesImpl() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.collectionReflectionUtil == null) {
      this.collectionReflectionUtil = CollectionReflectionUtilImpl.getInstance();
    }
  }

  @Override
  public CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionUtil is the collectionUtil to set
   */
  @Inject
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionUtil;
  }

  @Override
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

}
