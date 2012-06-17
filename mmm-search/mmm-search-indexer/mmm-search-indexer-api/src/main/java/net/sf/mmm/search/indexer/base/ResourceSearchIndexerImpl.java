/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.api.ContentParserService;
import net.sf.mmm.content.parser.base.ContentParserOptionsBean;
import net.sf.mmm.content.parser.impl.ContentParserServiceImpl;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the abstract base-implementation of the
 * {@link net.sf.mmm.search.indexer.api.ResourceSearchIndexer} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
@Singleton
public class ResourceSearchIndexerImpl extends AbstractResourceSearchIndexer {

  /** @see #getParserService() */
  private ContentParserService parserService;

  /** @see #getFileUtil() */
  private FileUtil fileUtil;

  /**
   * The constructor.
   */
  public ResourceSearchIndexerImpl() {

    super();
  }

  /**
   * @return the parserService
   */
  protected ContentParserService getParserService() {

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
  protected FileUtil getFileUtil() {

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
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
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
  public MutableSearchEntry createEntry(SearchIndexer indexer, DataResource resource,
      String resourceUri) {

    ContentParserOptions options = new ContentParserOptionsBean();
    return createEntry(indexer, resource, resourceUri, options);
  }

  /**
   * @see #createEntry(SearchIndexer, DataResource, String)
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param resourceUri is the {@link MutableSearchEntry#getUri() URI} for the
   *        {@link MutableSearchEntry entry}.
   * @param options are the {@link ContentParserOptionsBean options}.
   * @return the created {@link MutableSearchEntry}.
   */
  protected MutableSearchEntry createEntry(SearchIndexer indexer, DataResource resource,
      String resourceUri, ContentParserOptions options) {

    String filename = resource.getName();
    String extension = this.fileUtil.getExtension(filename);
    MutableSearchEntry entry = indexer.createEntry();
    entry.setUri(resourceUri);
    long fileSize = resource.getSize();
    entry.setSize(fileSize);
    ContentParser parser = this.parserService.getParser(extension);
    if (parser != null) {
      if (parser.getPrimaryKeys().contains(extension)) {
        // normalize the extension (e.g. "htm" -> "html")
        extension = parser.getExtension();
      }
      try {
        InputStream inputStream = resource.openStream();
        try {
          GenericContext context = parser.parse(inputStream, fileSize, options);
          String title = getStringProperty(context, ContentParser.VARIABLE_NAME_TITLE);
          if (title != null) {
            entry.setTitle(title);
          }
          String creator = getStringProperty(context, ContentParser.VARIABLE_NAME_CREATOR);
          if (creator != null) {
            entry.setCreator(creator);
          }
          String text = getStringProperty(context, ContentParser.VARIABLE_NAME_TEXT);
          if (text != null) {
            entry.setText(text);
          }
        } catch (Exception e) {
          getLogger().error("Failed to extract data from resource: " + resource.getUri(), e);
          // this is just a hack!!!
          // however it helps perfectly for debugging...
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          entry.setText(sw.toString());
        }
      } catch (RuntimeException e) {
        getLogger().error("Failed to open resource " + resource.getUri(), e);
      }
    }
    entry.setType(extension);
    return entry;
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location, EntryUpdateVisitor uriVisitor,
      DataResource locationResource, String nonUtfEncoding) {

    String uri = getEntryUri(resource, location, locationResource);
    uriVisitor.visitIndexedEntryUri(uri, changeType);
    if (changeType == null) {
      return;
    }
    String sourceId = null;
    SearchSource source = location.getSource();
    if (source != null) {
      sourceId = source.getId();
    }
    switch (changeType) {
      case REMOVE:
        getLogger().debug("Removing " + resource.getUri());
        indexer.removeByUri(uri, sourceId);
        return;
      case ADD:
        getLogger().debug("Adding " + resource.getUri());
        break;
      case UPDATE:
        getLogger().debug("Updating " + resource.getUri());
        break;
      default :
        throw new IllegalCaseException(ChangeType.class, changeType);
    }
    ContentParserOptionsBean options = new ContentParserOptionsBean();
    String encoding = location.getEncoding();
    if (encoding == null) {
      if (nonUtfEncoding != null) {
        options.setEncoding(nonUtfEncoding);
      }
      options.setDisableUtfDetection(false);
    } else {
      options.setEncoding(encoding);
      options.setDisableUtfDetection(true);
    }
    MutableSearchEntry entry = createEntry(indexer, resource, uri, options);
    if (sourceId != null) {
      entry.setSource(sourceId);
    }
    if (changeType == ChangeType.UPDATE) {
      indexer.update(entry);
    } else {
      indexer.add(entry);
    }
  }
}
