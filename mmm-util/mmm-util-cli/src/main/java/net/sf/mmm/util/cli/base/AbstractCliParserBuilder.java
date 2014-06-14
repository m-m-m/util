/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import javax.inject.Inject;

import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliParserBuilder;
import net.sf.mmm.util.cli.api.CliParserExcepiton;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.impl.CollectionFactoryManagerImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.text.api.LineWrapper;
import net.sf.mmm.util.text.base.DefaultLineWrapper;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * This is the abstract base implementation of the {@link CliParserBuilder} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCliParserBuilder extends AbstractLoggableComponent implements CliParserBuilder,
    CliParserDependencies {

  /** @see #getDescriptorBuilderFactory() */
  private PojoDescriptorBuilderFactory descriptorBuilderFactory;

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /** @see #getNlsMessageFactory() */
  private NlsMessageFactory nlsMessageFactory;

  /** @see #getNlsTemplateResolver() */
  private NlsTemplateResolver nlsTemplateResolver;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #getAnnotationUtil() */
  private AnnotationUtil annotationUtil;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getCollectionReflectionUtil() */
  private CollectionReflectionUtil collectionReflectionUtil;

  /** @see #getCollectionFactoryManager() */
  private CollectionFactoryManager collectionFactoryManager;

  /** @see #getConverter() */
  private ComposedValueConverter converter;

  /** @see #getLineWrapper() */
  private LineWrapper lineWrapper;

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
    if (this.nlsMessageFactory == null) {
      this.nlsMessageFactory = NlsAccess.getFactory();
    }
    // if (this.nlsTemplateResolver == null) {
    // this.nlsTemplateResolver = null;
    // }
    if (this.converter == null) {
      this.converter = DefaultComposedValueConverter.getInstance();
    }
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (this.collectionReflectionUtil == null) {
      this.collectionReflectionUtil = CollectionReflectionUtilImpl.getInstance();
    }
    if (this.collectionFactoryManager == null) {
      this.collectionFactoryManager = CollectionFactoryManagerImpl.getInstance();
    }
    if (this.lineWrapper == null) {
      DefaultLineWrapper wrapper = new DefaultLineWrapper();
      wrapper.initialize();
      this.lineWrapper = wrapper;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CliParser build(Object pojo) {

    if (pojo == null) {
      throw new NlsNullPointerException("pojo");
    }
    try {
      CliState state = new CliState(pojo.getClass(), this.descriptorBuilderFactory, getLogger(), getReflectionUtil(),
          getAnnotationUtil());
      CliParser parser = buildInternal(pojo, state);
      return parser;
    } catch (Exception e) {
      throw new CliParserExcepiton(e, pojo.getClass());
    }
  }

  /**
   * This method implements {@link #build(Object)} internally.
   *
   * @param state is the {@link AbstractCliParser#getState() state object}.
   * @param cliState is the according {@link CliState}.
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
  @Inject
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

  /**
   * {@inheritDoc}
   */
  @Override
  public CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * @param collectionFactoryManager is the collectionFactoryManager to set
   */
  @Inject
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the stringUtil to set
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AnnotationUtil getAnnotationUtil() {

    return this.annotationUtil;
  }

  /**
   * @param annotationUtil is the annotationUtil to set
   */
  @Inject
  public void setAnnotationUtil(AnnotationUtil annotationUtil) {

    getInitializationState().requireNotInitilized();
    this.annotationUtil = annotationUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionReflectionUtil is the collectionReflectionUtil to set
   */
  @Inject
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionReflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessageFactory getNlsMessageFactory() {

    return this.nlsMessageFactory;
  }

  /**
   * @param nlsMessageFactory is the nlsMessageFactory to set
   */
  @Inject
  public void setNlsMessageFactory(NlsMessageFactory nlsMessageFactory) {

    getInitializationState().requireNotInitilized();
    this.nlsMessageFactory = nlsMessageFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsTemplateResolver getNlsTemplateResolver() {

    return this.nlsTemplateResolver;
  }

  /**
   * @param nlsTemplateResolver is the {@link NlsTemplateResolver} to use.
   */
  public void setNlsTemplateResolver(NlsTemplateResolver nlsTemplateResolver) {

    this.nlsTemplateResolver = nlsTemplateResolver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComposedValueConverter getConverter() {

    return this.converter;
  }

  /**
   * @param converter is the converter to set
   */
  @Inject
  public void setConverter(ComposedValueConverter converter) {

    getInitializationState().requireNotInitilized();
    this.converter = converter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LineWrapper getLineWrapper() {

    return this.lineWrapper;
  }

  /**
   * @param lineWrapper is the lineWrapper to set
   */
  @Inject
  public void setLineWrapper(LineWrapper lineWrapper) {

    getInitializationState().requireNotInitilized();
    this.lineWrapper = lineWrapper;
  }

}
