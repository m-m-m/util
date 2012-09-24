/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;

/**
 * This is the implementation of the {@link IndexerDependencies}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(IndexerDependencies.CDI_NAME)
@Singleton
public class IndexerDependenciesImpl extends AbstractComponent implements IndexerDependencies {

  /** @see #getBrowsableResourceFactory() */
  private BrowsableResourceFactory browsableResourceFactory;

  /** @see #getResourceSearchIndexer() */
  private ResourceSearchIndexer resourceSearchIndexer;

  /**
   * The constructor.
   */
  public IndexerDependenciesImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    return this.browsableResourceFactory;
  }

  /**
   * @param browsableResourceFactory is the browsableResourceFactory to set
   */
  @Inject
  public void setBrowsableResourceFactory(BrowsableResourceFactory browsableResourceFactory) {

    getInitializationState().requireNotInitilized();
    this.browsableResourceFactory = browsableResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResourceSearchIndexer getResourceSearchIndexer() {

    return this.resourceSearchIndexer;
  }

  /**
   * @param resourceSearchIndexer is the resourceSearchIndexer to set
   */
  @Inject
  public void setResourceSearchIndexer(ResourceSearchIndexer resourceSearchIndexer) {

    getInitializationState().requireNotInitilized();
    this.resourceSearchIndexer = resourceSearchIndexer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.browsableResourceFactory == null) {
      BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
      impl.initialize();
      this.browsableResourceFactory = impl;
    }
    if (this.resourceSearchIndexer == null) {
      ResourceSearchIndexerImpl impl = new ResourceSearchIndexerImpl();
      impl.initialize();
      this.resourceSearchIndexer = impl;
    }
  }

}
