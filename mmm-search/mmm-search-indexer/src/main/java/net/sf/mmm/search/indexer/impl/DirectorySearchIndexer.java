/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.NoOpLog;

import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.search.parser.api.ContentParserService;
import net.sf.mmm.search.parser.impl.ContentParserServiceImpl;
import net.sf.mmm.util.filter.FileFilterAdapter;
import net.sf.mmm.util.filter.Filter;
import net.sf.mmm.util.filter.FilterRuleChainParser;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DirectorySearchIndexer {

  /** @see #getIndexer() */
  private SearchIndexer indexer;

  /** @see #getFilter() */
  private FileFilter filter;

  /** @see #getLogger() */
  private Log logger;

  /** the parser service */
  private ContentParserService parserService;

  /**
   * The constructor.
   */
  public DirectorySearchIndexer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param indexer
   */
  public DirectorySearchIndexer(SearchIndexer indexer) {

    super();
    this.indexer = indexer;
  }

  /**
   * @return the indexer
   */
  public SearchIndexer getIndexer() {

    return this.indexer;
  }

  /**
   * @param indexer
   *        the indexer to set
   */
  @Resource
  public void setIndexer(SearchIndexer indexer) {

    this.indexer = indexer;
  }

  /**
   * @return the filter
   */
  public FileFilter getFilter() {

    return this.filter;
  }

  /**
   * @see #setFilterByConfiguration(Reader)
   * 
   * @param filter
   *        the filter to set
   */
  @Resource
  public void setFilter(FileFilter filter) {

    this.filter = filter;
  }

  /**
   * This method sets the filter by a reader pointing to a configuration file.
   * For the format of the configuration see {@link FilterRuleChainParser}.
   * 
   * @param reader
   *        is a fresh reader to the configuration.It will be closed at the end
   *        of this method (on success and in an exceptional state).
   * @throws IOException
   *         if the operation failed with an I/O error.
   */
  public void setFilterByConfiguration(Reader reader) throws IOException {

    FilterRuleChainParser parser = new FilterRuleChainParser();
    Filter<String> stringFilter = parser.parse(reader);
    this.filter = FileFilterAdapter.convertStringFilter(stringFilter);
  }

  /**
   * @return the logger
   */
  public Log getLogger() {

    return this.logger;
  }

  /**
   * @param logger
   *        the logger to set
   */
  @Resource
  public void setLogger(Log logger) {

    this.logger = logger;
  }

  /**
   * @return the parserService
   */
  public ContentParserService getParserService() {

    return this.parserService;
  }

  /**
   * @param parserService
   *        the parserService to set
   */
  @Resource
  public void setParserService(ContentParserService parserService) {

    this.parserService = parserService;
  }

  /**
   * This method initializes this object.
   */
  @PostConstruct
  public void initialize() {

    if (this.parserService == null) {
      this.parserService = new ContentParserServiceImpl();
    }
    if (this.logger == null) {
      this.logger = new NoOpLog();
    }
  }

  protected String getExtension(String filename) {

    int lastDot = filename.lastIndexOf('.');
    String extension;
    if (lastDot > 0) {
      extension = filename.substring(lastDot + 1).toLowerCase();
    } else {
      extension = "";
    }
    return extension;
  }

  public void indexDirectory(File directory) {

    indexDirectory(null, directory);
  }

  public void indexDirectory(String source, File directory) {

    indexDirectory(source, directory, "");
  }

  public void indexDirectory(String source, File directory, String relativePath) {

    for (File child : directory.listFiles()) {
      if ((this.filter == null) || (this.filter.accept(child))) {
        if (child.isDirectory()) {
          indexDirectory(source, child, relativePath + "/" + child.getName());
        } else if (child.isFile()) {
          indexFile(source, child, relativePath);
        }
      } else {
        this.logger.debug("Filtered " + child.getPath());
      }
    }
  }

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

  public void indexFile(String source, File file, String relativePath) {

    this.logger.debug("Indexing " + file.getPath());
    String filename = file.getName();
    String fullPath = relativePath + "/" + filename;
    long fileSize = file.length();
    String extension = getExtension(filename);
    MutableSearchEntry entry = this.indexer.createEntry();
    entry.setUri(fullPath);
    entry.setSize(fileSize);
    ContentParser parser = null;
    if (extension != null) {
      entry.setType(extension);
      parser = this.parserService.getParser(extension);
    }
    if (parser != null) {
      try {
        InputStream inputStream = new FileInputStream(file);
        try {
          Properties properties = parser.parse(inputStream, fileSize);
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
          this.logger.error("Failed to extract data from file: " + file.getPath(), e);
          // TODO: this is just a temporary hack!!!
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          entry.setText(sw.toString());
        }
      } catch (FileNotFoundException e) {
        this.logger.error("Filed disappeared while indexing " + file.getPath());
      }
    }
    if (source != null) {
      entry.setSource(source);
    }
    this.indexer.add(entry);
  }

}
