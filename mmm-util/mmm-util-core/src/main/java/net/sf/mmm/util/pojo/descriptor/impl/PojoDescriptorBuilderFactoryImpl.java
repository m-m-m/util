/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoFieldIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoMethodIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class PojoDescriptorBuilderFactoryImpl extends AbstractPojoDescriptorBuilderFactory {

  /** @see #getConfiguration() */
  private ExtendedPojoDescriptorConfigurationImpl configuration;

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoDescriptorBuilder createDescriptorBuilder(VisibilityModifier methodVisibility,
      VisibilityModifier fieldVisibility) {

    PojoDescriptorBuilderImpl descriptorBuilder = new PojoDescriptorBuilderImpl();
    if (methodVisibility == null) {
      descriptorBuilder.setMethodIntrospector(new NoPojoMethodIntrospector());
    } else {
      descriptorBuilder.setMethodIntrospector(new PojoMethodIntrospectorImpl(methodVisibility,
          false));
    }
    if (fieldVisibility == null) {
      descriptorBuilder.setFieldIntrospector(new NoPojoFieldIntrospector());
    } else {
      descriptorBuilder.setFieldIntrospector(new PojoFieldIntrospectorImpl(fieldVisibility, false));
    }
    descriptorBuilder.setConfiguration(this.configuration);
    descriptorBuilder.initialize();
    return descriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (this.configuration == null) {
      this.configuration = new ExtendedPojoDescriptorConfigurationImpl();
      this.configuration.initialize();
    }
  }

  /**
   * This method sets the {@link ExtendedPojoDescriptorConfigurationImpl}.
   * 
   * @param configuration is the {@link ExtendedPojoDescriptorConfigurationImpl}
   *        .
   */
  @Inject
  public void setConfiguration(ExtendedPojoDescriptorConfigurationImpl configuration) {

    getInitializationState().requireNotInitilized();
    this.configuration = configuration;
  }

  /**
   * This method gets the {@link ExtendedPojoDescriptorConfigurationImpl}.
   * 
   * @return the {@link ExtendedPojoDescriptorConfigurationImpl}.
   */
  protected ExtendedPojoDescriptorConfigurationImpl getConfiguration() {

    return this.configuration;
  }

}
