/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.state.SearchIndexState;
import net.sf.mmm.search.indexer.base.AbstractConfiguredSearchIndexer;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class ConfiguredSearchIndexerImpl extends AbstractConfiguredSearchIndexer {

  /** @see #getBrowsableResourceFactory() */
  private BrowsableResourceFactory browsableResourceFactory;

  /**
   * The constructor.
   */
  public ConfiguredSearchIndexerImpl() {

    super();
  }

  /**
   * @return the browsableResourceFactory
   */
  protected BrowsableResourceFactory getBrowsableResourceFactory() {

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
   * This method initializes this object.
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
    if (this.browsableResourceFactory == null) {
      BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
      impl.initialize();
      this.browsableResourceFactory = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexer searchIndexer, SearchIndexState state,
      boolean forceFullIndexing, SearchIndexDataLocation location, Collection<String> resourceUris) {

    String locationUri = location.getLocation();
    BrowsableResource resource = this.browsableResourceFactory.createBrowsableResource(locationUri);
    Set<String> visitedResources = new HashSet<String>();
    indexRecursive(searchIndexer, location, resource, visitedResources);
  }

  /**
   * This method starts the indexing from the given <code>directory</code>
   * adding the given <code>source</code> as metadata.
   * 
   * @param searchIndexer is the {@link SearchIndexer} to use.
   * @param location is the {@link SearchIndexDataLocation}.
   * @param resource is the directory to index recursively.
   * @param visitedResources is the {@link Set} of
   *        {@link net.sf.mmm.util.resource.api.DataResource#getUri() URIs} that
   *        have already been indexed. Should be an empty {@link Set} on initial
   *        call of this method.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   */
  public void indexRecursive(SearchIndexer searchIndexer, SearchIndexDataLocation location,
      BrowsableResource resource, Set<String> visitedResources) {

    String uri = resource.getUri();
    if (!visitedResources.contains(uri)) {
      visitedResources.add(uri);
      if (resource.isData()) {
        indexData(searchIndexer, location, resource);
      }
      for (BrowsableResource child : resource.getChildResources()) {
        String childUri = child.getUri().replace('\\', '/');
        if (location.getFilter().accept(childUri)) {
          indexRecursive(searchIndexer, location, child, visitedResources);
        } else {
          getLogger().debug("Filtered " + childUri);
        }
      }
    }
  }

}
