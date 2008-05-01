/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import javax.annotation.Resource;

import net.sf.mmm.util.component.AbstractComponent;
import net.sf.mmm.util.reflect.CollectionUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the implementation of the {@link PojoDescriptorConfiguration}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorConfigurationImpl extends AbstractComponent implements
    PojoDescriptorConfiguration {

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getCollectionUtil() */
  private CollectionUtil collectionUtil;

  /**
   * The constructor.
   */
  public PojoDescriptorConfigurationImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public CollectionUtil getCollectionUtil() {

    return this.collectionUtil;
  }

  /**
   * @param collectionUtil is the collectionUtil to set
   */
  @Resource
  public void setCollectionUtil(CollectionUtil collectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionUtil = collectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Resource
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtil.getInstance();
    }
    if (this.collectionUtil == null) {
      this.collectionUtil = CollectionUtil.getInstance();
    }
  }

}
