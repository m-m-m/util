/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import javax.inject.Inject;

import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the abstract base-implementation of the {@link ResourceSearchIndexer}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractResourceSearchIndexer extends AbstractLoggableComponent implements
    ResourceSearchIndexer {

  /** @see #setResourceFactory(DataResourceFactory) */
  private DataResourceFactory resourceFactory;

  /**
   * The constructor.
   */
  public AbstractResourceSearchIndexer() {

    super();
  }

  /**
   * @param resourceFactory is the resourceFactory to set
   */
  @Inject
  public void setResourceFactory(DataResourceFactory resourceFactory) {

    getInitializationState().requireNotInitilized();
    this.resourceFactory = resourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.resourceFactory == null) {
      BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
      impl.initialize();
      this.resourceFactory = impl;
    }
  }

  /**
   * This method determines the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() entry URI} for the given
   * <code>resourceUri</code>.
   * 
   * @param resource is the {@link DataResource} for which the
   *        {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} is requested.
   * @param location is the {@link SearchIndexerDataLocation} used to check
   *        {@link SearchIndexerDataLocation#isAbsoluteUris()}.
   * @param locationResource is the {@link DataResource} for
   *        {@link SearchIndexerDataLocation#getLocationUri()}.
   * @return the {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} for the
   *         <code>resource</code> to index.
   */
  protected String getEntryUri(DataResource resource, SearchIndexerDataLocation location,
      DataResource locationResource) {

    String uri = resource.getUri();
    if (!location.isAbsoluteUris()) {
      String locationUri = locationResource.getUri();
      if (uri.startsWith(locationUri)) {
        uri = uri.substring(locationUri.length());
        if (uri.startsWith("/") || uri.startsWith("\\")) {
          uri = uri.substring(1);
        }
        String baseUri = location.getBaseUri();
        if (baseUri == null) {
          baseUri = "";
        }
        if ((baseUri.length() > 0) && !baseUri.endsWith("/") && !baseUri.endsWith("\\")) {
          baseUri = baseUri + "/";
        }
        uri = baseUri + uri;
      } else {
        // this may happen if we crawl the web and a link goes to an other site
      }
    }
    Transformer<String> uriTransformer = location.getUriTransformer();
    if (uriTransformer != null) {
      uri = uriTransformer.transform(uri);
    }
    return uri;
  }

  /**
   * This method gets the property <code>key</code> from the given
   * <code>properties</code>. It will also {@link String#trim() trim} the
   * properties value.
   * 
   * @param context is where to get the property from.
   * @param key is the name of the requested property.
   * @return the trimmed property or <code>null</code> if the property is NOT
   *         set or its trimmed value is the empty string.
   */
  protected String getStringProperty(GenericContext context, String key) {

    String value = context.getVariable(key, String.class);
    if (value != null) {
      value = value.trim();
      if (value.length() == 0) {
        value = null;
      }
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location) {

    index(indexer, resource, changeType, location, EntryUpdateVisitorDummy.INSTANCE);
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location, EntryUpdateVisitor uriVisitor) {

    DataResource locationFolder = this.resourceFactory
        .createDataResource(location.getLocationUri());
    index(indexer, resource, changeType, location, uriVisitor, locationFolder);
  }
}
