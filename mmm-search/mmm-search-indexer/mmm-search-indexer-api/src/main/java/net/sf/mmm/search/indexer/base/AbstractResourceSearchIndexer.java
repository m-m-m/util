/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.Properties;

import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.resource.api.DataResource;
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

  /**
   * The constructor.
   */
  public AbstractResourceSearchIndexer() {

    super();
  }

  /**
   * This method determines the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() entry URI} for the given
   * <code>resourceUri</code>.
   * 
   * @param resourceUri is the normalized {@link DataResource#getUri()
   *        resource-URI}.
   * @param location is the {@link SearchIndexerDataLocation}.
   * @return the {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} for the
   *         entry to index.
   */
  protected String getEntryUri(String resourceUri, SearchIndexerDataLocation location) {

    String uri = resourceUri;
    if (!location.isAbsoluteUris()) {
      String locationUri = location.getLocationUri().replace('\\', '/');
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
   * @param properties is where to get the property from.
   * @param key is the name of the requested property.
   * @return the trimmed property or <code>null</code> if the property is NOT
   *         set or its trimmed value is the empty string.
   */
  protected String getProperty(Properties properties, String key) {

    String value = properties.getProperty(key);
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
}
