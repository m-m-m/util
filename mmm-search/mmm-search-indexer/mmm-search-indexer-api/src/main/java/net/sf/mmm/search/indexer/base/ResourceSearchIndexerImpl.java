/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.api.ContentParser;
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
  public void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location, EntryUpdateVisitor uriVisitor) {

    String resourceUri = resource.getUri().replace('\\', '/');
    String uri = getEntryUri(resourceUri, location);
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
        getLogger().debug("Removing " + resourceUri);
        indexer.removeByUri(uri, sourceId);
        return;
      case ADD:
        getLogger().debug("Adding " + resourceUri);
        break;
      case UPDATE:
        getLogger().debug("Updating " + resourceUri);
        break;
      default :
        throw new IllegalCaseException(ChangeType.class, changeType);
    }
    String filename = resource.getName();
    String extension = this.fileUtil.getExtension(filename);
    MutableSearchEntry entry = indexer.createEntry();
    entry.setUri(uri);
    if (sourceId != null) {
      entry.setSource(sourceId);
    }
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
          ContentParserOptionsBean options = new ContentParserOptionsBean();
          String encoding = location.getEncoding();
          if (encoding != null) {
            options.setEncoding(encoding);
          }
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

    if (changeType == ChangeType.UPDATE) {
      indexer.update(entry);
    } else {
      indexer.add(entry);
    }
  }
}
