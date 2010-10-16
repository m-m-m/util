/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.inject.Inject;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.content.parser.impl.ContentParserServiceImpl;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerBuilder;
import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationReaderImpl;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the implementation of the {@link ConfiguredSearchIndexer}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfiguredSearchIndexer extends AbstractLoggable implements
    ConfiguredSearchIndexer {

  /** @see #getSearchIndexerManager() */
  private SearchIndexerBuilder searchIndexerManager;

  /** @see #getSearchIndexerConfigurationReader() */
  private SearchIndexerConfigurationReader searchIndexerConfigurationReader;

  /** @see #getParserService() */
  private ContentParserService parserService;

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public AbstractConfiguredSearchIndexer() {

    super();
  }

  /**
   * @return the searchIndexerManager
   */
  protected SearchIndexerBuilder getSearchIndexerManager() {

    return this.searchIndexerManager;
  }

  /**
   * @param searchIndexerManager is the searchIndexerManager to set
   */
  @Inject
  public void setSearchIndexerManager(SearchIndexerBuilder searchIndexerManager) {

    this.searchIndexerManager = searchIndexerManager;
  }

  /**
   * @return the searchIndexerConfigurationReader
   */
  protected SearchIndexerConfigurationReader getSearchIndexerConfigurationReader() {

    return this.searchIndexerConfigurationReader;
  }

  /**
   * @param searchIndexerConfigurationReader is the
   *        searchIndexerConfigurationReader to set
   */
  @Inject
  public void setSearchIndexerConfigurationReader(
      SearchIndexerConfigurationReader searchIndexerConfigurationReader) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerConfigurationReader = searchIndexerConfigurationReader;
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
    if (this.searchIndexerConfigurationReader == null) {
      SearchIndexerConfigurationReaderImpl readerImpl = new SearchIndexerConfigurationReaderImpl();
      readerImpl.initialize();
      this.searchIndexerConfigurationReader = readerImpl;
    }
    if (this.parserService == null) {
      ContentParserServiceImpl impl = new ContentParserServiceImpl();
      impl.initialize();
      this.parserService = impl;

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
        index(searchIndexer, location, false);
      }
    } finally {
      getLogger().debug("Optimizing index...");
      searchIndexer.optimize();
      getLogger().debug("Closing index...");
      searchIndexer.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void index(String configurationFile) {

    SearchIndexerConfiguration configuration = this.searchIndexerConfigurationReader
        .readConfiguration(configurationFile);
    index(configuration);
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

    String uri = resource.getUri().replace('\\', '/');
    getLogger().debug("Indexing " + uri);
    String filename = resource.getName();
    if (!location.isAbsoluteUris()) {
      String locationUri = location.getLocation().replace('\\', '/');
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
    if (parser == null) {
      parser = this.parserService.getGenericParser();
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
