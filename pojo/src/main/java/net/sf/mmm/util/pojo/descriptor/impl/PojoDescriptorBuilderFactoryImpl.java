/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import javax.inject.Inject;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.ExtendedPojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoFieldIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoMethodIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of {@link PojoDescriptorBuilderFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoDescriptorBuilderFactoryImpl extends AbstractPojoDescriptorBuilderFactory {

  private  ExtendedPojoDescriptorDependencies dependencies;

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderFactoryImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link PojoDescriptorBuilderFactory}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static PojoDescriptorBuilderFactory getInstance() {

    PojoDescriptorBuilderFactory instance = AbstractPojoDescriptorBuilderFactory.getInstance();
    if (instance == null) {
      synchronized (PojoDescriptorBuilderFactoryImpl.class) {
        if (instance == null) {
          PojoDescriptorBuilderFactoryImpl impl = new PojoDescriptorBuilderFactoryImpl();
          instance = impl;
          setInstance(impl);
          impl.initialize();
        }
      }
    }
    return instance;
  }

  @Override
  public PojoDescriptorBuilder createDescriptorBuilder(VisibilityModifier methodVisibility,
      VisibilityModifier fieldVisibility) {

    PojoDescriptorBuilderImpl descriptorBuilder = new PojoDescriptorBuilderImpl();
    if (methodVisibility == null) {
      descriptorBuilder.setMethodIntrospector(new NoPojoMethodIntrospector());
    } else {
      descriptorBuilder.setMethodIntrospector(new PojoMethodIntrospectorImpl(methodVisibility, false));
    }
    if (fieldVisibility == null) {
      descriptorBuilder.setFieldIntrospector(new NoPojoFieldIntrospector());
    } else {
      descriptorBuilder.setFieldIntrospector(new PojoFieldIntrospectorImpl(fieldVisibility, false));
    }
    descriptorBuilder.setConfiguration(this.dependencies);
    descriptorBuilder.initialize();
    return descriptorBuilder;
  }

  @Override
  protected void doInitialize() {

    if (this.dependencies == null) {
      ExtendedPojoDescriptorDependenciesImpl impl = new ExtendedPojoDescriptorDependenciesImpl();
      impl.initialize();
      this.dependencies = impl;
    }
  }

  /**
   * This method sets the {@link ExtendedPojoDescriptorDependencies}.
   *
   * @param dependencies are the {@link ExtendedPojoDescriptorDependencies} .
   */
  @Inject
  public void setDependencies(ExtendedPojoDescriptorDependencies dependencies) {

    getInitializationState().requireNotInitilized();
    this.dependencies = dependencies;
  }

  /**
   * This method gets the {@link ExtendedPojoDescriptorDependenciesImpl}.
   *
   * @return the {@link ExtendedPojoDescriptorDependenciesImpl}.
   */
  protected ExtendedPojoDescriptorDependencies getDependencies() {

    return this.dependencies;
  }

}
