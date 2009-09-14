/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfigurationImpl;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorAddBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetIndexedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetMappedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorRemoveBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetIndexedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetMappedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSizeBuilder;

/**
 * This class extends {@link PojoDescriptorConfigurationImpl} with additional
 * components to inject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ExtendedPojoDescriptorConfigurationImpl extends PojoDescriptorConfigurationImpl {

  /** @see #getAccessorBuilders() */
  private Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders;

  /** @see #getDescriptorEnhancer() */
  private PojoDescriptorEnhancer descriptorEnhancer;

  /**
   * The constructor.
   */
  public ExtendedPojoDescriptorConfigurationImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.accessorBuilders == null) {
      this.accessorBuilders = new ArrayList<PojoPropertyAccessorBuilder<?>>();
      this.accessorBuilders.add(new PojoPropertyAccessorGetBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorAddBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorRemoveBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorGetIndexedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorGetMappedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetIndexedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetMappedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSizeBuilder());
      this.accessorBuilders = Collections.unmodifiableCollection(this.accessorBuilders);
    }
    if (this.descriptorEnhancer == null) {
      DefaultPojoDescriptorEnhancer enhancer = new DefaultPojoDescriptorEnhancer();
      enhancer.setConfiguration(this);
      enhancer.initialize();
      this.descriptorEnhancer = enhancer;
    }
  }

  /**
   * This method gets the accessor-builders used to create the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor
   * accessors} for
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor
   * properties} of a {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor
   * POJO}.
   * 
   * @return the accessorBuilders.
   */
  public Collection<PojoPropertyAccessorBuilder<?>> getAccessorBuilders() {

    return this.accessorBuilders;
  }

  /**
   * This method sets the {@link #getAccessorBuilders() accessor-builders}.
   * 
   * @param accessorBuilders is a collection with the accessorBuilders to use.
   *        It must NOT contain two entries with the same
   *        {@link PojoPropertyAccessorBuilder#getMode() mode}.
   */
  @Resource
  public void setAccessorBuilders(Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders) {

    getInitializationState().requireNotInitilized();
    Set<PojoPropertyAccessorMode<?>> modeSet = new HashSet<PojoPropertyAccessorMode<?>>();
    List<PojoPropertyAccessorBuilder<?>> builderList = new ArrayList<PojoPropertyAccessorBuilder<?>>();
    for (PojoPropertyAccessorBuilder<?> builder : accessorBuilders) {
      PojoPropertyAccessorMode<?> mode = builder.getMode();
      boolean added = modeSet.add(mode);
      if (!added) {
        throw new DuplicateObjectException(builder, mode);
      }
      builderList.add(builder);
    }
    this.accessorBuilders = Collections.unmodifiableCollection(builderList);
  }

  /**
   * This method gets the {@link PojoDescriptorEnhancer} to use.
   * 
   * @return the {@link PojoDescriptorEnhancer}.
   */
  public PojoDescriptorEnhancer getDescriptorEnhancer() {

    return this.descriptorEnhancer;
  }

  /**
   * This method sets the {@link #getDescriptorEnhancer() descriptor-enhancer}.
   * 
   * @param descriptorEnhancer is the {@link PojoDescriptorEnhancer} to set.
   */
  @Resource
  public void setDescriptorEnhancer(PojoDescriptorEnhancer descriptorEnhancer) {

    getInitializationState().requireNotInitilized();
    this.descriptorEnhancer = descriptorEnhancer;
  }

}
