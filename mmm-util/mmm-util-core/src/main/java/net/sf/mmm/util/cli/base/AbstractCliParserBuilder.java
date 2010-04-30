/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import javax.annotation.Resource;

import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliParserBuilder;
import net.sf.mmm.util.cli.api.CliParserExcepiton;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;

/**
 * This is the abstract base implementation of the {@link CliParserBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractCliParserBuilder extends AbstractLoggable implements CliParserBuilder {

  /** @see #getDescriptorBuilderFactory() */
  private PojoDescriptorBuilderFactory descriptorBuilderFactory;

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /**
   * The constructor.
   */
  public AbstractCliParserBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.descriptorBuilderFactory == null) {
      PojoDescriptorBuilderFactoryImpl factory = new PojoDescriptorBuilderFactoryImpl();
      factory.initialize();
      this.descriptorBuilderFactory = factory;
    }
  }

  /**
   * {@inheritDoc}
   */
  public CliParser build(Object pojo) {

    if (pojo == null) {
      throw new NlsNullPointerException("pojo");
    }
    try {
      CliState state = new CliState(pojo.getClass(), this.descriptorBuilderFactory);
      CliParser parser = buildInternal(pojo, state);
      return parser;
    } catch (Exception e) {
      throw new CliParserExcepiton(e, pojo.getClass());
    }
  }

  /**
   * This method implements {@link #build(Object)} internally.
   * @param state TODO
   * @param cliState is the {@link CliState}.
   * 
   * @return the new {@link CliParser}.
   */
  protected abstract CliParser buildInternal(Object state, CliState cliState);

  /**
   * @return the descriptorBuilderFactory
   */
  protected PojoDescriptorBuilderFactory getDescriptorBuilderFactory() {

    return this.descriptorBuilderFactory;
  }

  /**
   * @param descriptorBuilderFactory is the descriptorBuilderFactory to set
   */
  @Resource
  public void setDescriptorBuilderFactory(PojoDescriptorBuilderFactory descriptorBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilderFactory = descriptorBuilderFactory;
  }

  /**
   * @return the descriptorBuilder
   */
  protected PojoDescriptorBuilder getDescriptorBuilder() {

    return this.descriptorBuilder;
  }

  /**
   * @param descriptorBuilder is the descriptorBuilder to set
   */
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

}
