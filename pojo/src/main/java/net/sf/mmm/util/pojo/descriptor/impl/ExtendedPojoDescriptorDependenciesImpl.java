/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.ExtendedPojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependenciesImpl;
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
 * This class extends {@link PojoDescriptorDependenciesImpl} with additional components to inject.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (renamed, 1.1.0)
 */
public class ExtendedPojoDescriptorDependenciesImpl extends PojoDescriptorDependenciesImpl
    implements ExtendedPojoDescriptorDependencies {

  private Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders;

  private PojoDescriptorEnhancer descriptorEnhancer;

  /**
   * The constructor.
   */
  public ExtendedPojoDescriptorDependenciesImpl() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.accessorBuilders == null) {
      this.accessorBuilders = new ArrayList<>();
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
      DefaultPojoDescriptorEnhancer impl = new DefaultPojoDescriptorEnhancer();
      impl.setDependencies(this);
      impl.initialize();
      this.descriptorEnhancer = impl;
    }
  }

  /**
   * This method gets the accessor-builders used to create the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor accessors} for
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties} of a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
   *
   * @return the accessorBuilders.
   */
  @Override
  public Collection<PojoPropertyAccessorBuilder<?>> getAccessorBuilders() {

    return this.accessorBuilders;
  }

  /**
   * This method sets the {@link #getAccessorBuilders() accessor-builders}.
   *
   * @param accessorBuilders is a collection with the accessorBuilders to use. It must NOT contain two entries with the
   *        same {@link PojoPropertyAccessorBuilder#getMode() mode}.
   */
  // @Inject
  public void setAccessorBuilders(Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders) {

    getInitializationState().requireNotInitilized();
    Set<PojoPropertyAccessorMode<?>> modeSet = new HashSet<>();
    List<PojoPropertyAccessorBuilder<?>> builderList = new ArrayList<>();
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
   * @return the {@link PojoDescriptorEnhancer} instance to use.
   */
  @Override
  public PojoDescriptorEnhancer getDescriptorEnhancer() {

    return this.descriptorEnhancer;
  }

  /**
   * @param descriptorEnhancer is the {@link PojoDescriptorEnhancer} to {@link Inject inject}.
   */
  @Inject
  public void setDescriptorEnhancer(PojoDescriptorEnhancer descriptorEnhancer) {

    getInitializationState().requireNotInitilized();
    this.descriptorEnhancer = descriptorEnhancer;
  }

}
