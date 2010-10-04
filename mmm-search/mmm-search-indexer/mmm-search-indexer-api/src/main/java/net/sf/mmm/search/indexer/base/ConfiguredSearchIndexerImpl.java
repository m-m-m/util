/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.content.parser.impl.ContentParserServiceImpl;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerManager;
import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationBean;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.file.api.FileNotExistsException;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.transformer.api.Transformer;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * This is the implementation of the {@link ConfiguredSearchIndexer}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class ConfiguredSearchIndexerImpl extends AbstractLoggable implements
    ConfiguredSearchIndexer {

  /** @see #getSearchIndexerManager() */
  private SearchIndexerManager searchIndexerManager;

  /** @see #getBrowsableResourceFactory() */
  private BrowsableResourceFactory browsableResourceFactory;

  /** @see #getParserService() */
  private ContentParserService parserService;

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public ConfiguredSearchIndexerImpl() {

    super();
  }

  /**
   * @return the searchIndexerManager
   */
  protected SearchIndexerManager getSearchIndexerManager() {

    return this.searchIndexerManager;
  }

  /**
   * @param searchIndexerManager is the searchIndexerManager to set
   */
  @Inject
  public void setSearchIndexerManager(SearchIndexerManager searchIndexerManager) {

    this.searchIndexerManager = searchIndexerManager;
  }

  /**
   * @return the browsableResourceFactory
   */
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
   * @return the parserService
   */
  public ContentParserService getParserService() {

    return this.parserService;
  }

  /**
   * @param parserService the parserService to set
   */
  @Inject
  public void setParserService(ContentParserService parserService) {

    getInitializationState().requireNotInitilized();
    this.parserService = parserService;
  }

  /**
   * @return the fileUtil
   */
  public FileUtil getFileUtil() {

    return this.fileUtil;
  }

  /**
   * @param fileUtil is the fileUtil to set
   */
  @Inject
  public void setFileUtil(FileUtil fileUtil) {

    getInitializationState().requireNotInitilized();
    this.fileUtil = fileUtil;
  }

  /**
   * This method initializes this object.
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
    if (this.searchIndexerManager == null) {
      throw new ResourceMissingException("searchIndexerManager");
    }
    if (this.parserService == null) {
      ContentParserServiceImpl impl = new ContentParserServiceImpl();
      impl.initialize();
      this.parserService = impl;

    }
    if (this.browsableResourceFactory == null) {
      BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
      impl.initialize();
      this.browsableResourceFactory = impl;
    }
    if (this.fileUtil == null) {
      this.fileUtil = FileUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerConfiguration configuration) {

    SearchIndexConfiguration searchIndexConfiguration = configuration.getSearchIndex();
    SearchIndexer searchIndexer = this.searchIndexerManager.createIndexer(searchIndexConfiguration);
    try {
      for (SearchIndexDataLocation location : configuration.getLocations()) {
        index(searchIndexer, location);
      }
    } finally {
      searchIndexer.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void index(String configurationFile) {

    String path = this.fileUtil.normalizePath(configurationFile);
    File file = new File(path);
    if (!file.isFile()) {
      throw new FileNotExistsException(file);
    }
    try {
      InputStream inStream = new FileInputStream(file);
      try {
        JAXBContext jaxbContext = JAXBContext.newInstance(SearchIndexerConfigurationBean.class);
        SearchIndexerConfigurationBean configuration = (SearchIndexerConfigurationBean) jaxbContext
            .createUnmarshaller().unmarshal(inStream);
        index(configuration);
      } finally {
        inStream.close();
      }
    } catch (JAXBException e) {
      throw new XmlInvalidException(e);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }

  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexer searchIndexer, SearchIndexDataLocation location) {

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
   * @param visitedResources is the {@link Set} of {@link DataResource#getUri()
   *        URIs} that have already been indexed. Should be an empty {@link Set}
   *        on initial call of this method.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   */
  public void indexRecursive(SearchIndexer searchIndexer, SearchIndexDataLocation location,
      BrowsableResource resource, Set<String> visitedResources) {

    String uri = resource.getUri();
    if (!visitedResources.contains(uri)) {
      visitedResources.add(uri);
      if (resource.isAvailable()) {
        indexData(searchIndexer, location, resource);
      }
      for (BrowsableResource child : resource.getChildResources()) {
        if (location.getFilter().accept(child.getUri())) {
          indexRecursive(searchIndexer, location, child, visitedResources);
        } else {
          getLogger().debug("Filtered " + child.getUri());
        }
      }
    }
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
   * This method indexes a single {@link DataResource}.
   * 
   * @param searchIndexer is the {@link SearchIndexer} to use.
   * @param location is the {@link SearchIndexDataLocation}.
   * @param resource is the {@link DataResource} to index.
   */
  public void indexData(SearchIndexer searchIndexer, SearchIndexDataLocation location,
      DataResource resource) {

    getLogger().debug("Indexing " + resource.getUri());
    String filename = resource.getName();
    String uri = resource.getUri();
    if (!location.isAbsoluteUris()) {
      String locationUri = location.getLocation();
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
    String extension = this.fileUtil.getExtension(filename);
    MutableSearchEntry entry = searchIndexer.createEntry();
    Transformer<String> uriTransformer = location.getUriTransformer();
    if (uriTransformer != null) {
      uri = uriTransformer.transform(uri);
    }
    entry.setUri(uri);
    long fileSize = resource.getSize();
    entry.setSize(fileSize);
    ContentParser parser = null;
    if (extension != null) {
      entry.setType(extension);
      parser = this.parserService.getParser(extension);
    }
    if (parser != null) {
      try {
        InputStream inputStream = resource.openStream();
        try {
          Properties properties = parser.parse(inputStream, fileSize, location.getEncoding());
          String title = getProperty(properties, ContentParser.PROPERTY_KEY_TITLE);
          if (title != null) {
            entry.setTitle(title);
          }
          String author = getProperty(properties, ContentParser.PROPERTY_KEY_AUTHOR);
          if (author != null) {
            entry.setAuthor(author);
          }
          String text = getProperty(properties, ContentParser.PROPERTY_KEY_TEXT);
          if (text != null) {
            entry.setText(text);
          }
        } catch (Exception e) {
          getLogger().error("Failed to extract data from resource: " + resource.getUri(), e);
          // TODO: this is just a temporary hack!!!
          // however it helps perfectly for debugging...
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          entry.setText(sw.toString());
        }
      } catch (RuntimeException e) {
        getLogger().error("Failed to open resource " + resource.getUri(), e);
      }
    }
    SearchSource source = location.getSource();
    if (source != null) {
      entry.setSource(source.getId());
    }
    searchIndexer.add(entry);
  }
}
