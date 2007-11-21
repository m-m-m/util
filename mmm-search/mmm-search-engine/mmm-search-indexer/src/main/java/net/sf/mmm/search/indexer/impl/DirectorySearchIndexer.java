/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import net.sf.mmm.util.component.ResourceMissingException;
import net.sf.mmm.util.file.FileUtil;
import net.sf.mmm.util.transformer.Transformer;

/**
 * This class contains functionality to recursively walk through directories and
 * add contained files to a search index.<br>
 * Only directories that are {@link FileFilter#accept(File) accepted} by the
 * {@link #setFilter(FileFilter) file-filter} are proceeded by this indexer and
 * all containing files and folders will be processed recursively. If a file is
 * hit and {@link FileFilter#accept(File) accepted} by the
 * {@link #setFilter(FileFilter) file-filter} it will be added to the index.<br>
 * The relative path to the file is used as the
 * {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of the according
 * {@link net.sf.mmm.search.api.SearchEntry entry} in the search-index. An
 * {@link #getUriRewriter() URI-rewriter} can be set to rewrite the URI if it
 * differs from the path to access the file online. E.g. the relevant files in
 * the <code>data</code> directory of a twiki installation have the extension
 * <code>.txt</code> while their URLs do NOT contain this extension.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DirectorySearchIndexer {

  /** @see #getIndexer() */
  private SearchIndexer indexer;

  /** @see #getFilter() */
  private FileFilter filter;

  /** @see #getUriRewriter() */
  private Transformer<String> uriRewriter;

  /** @see #getLogger() */
  private Log logger;

  /** the parser service */
  private ContentParserService parserService;

  /** @see #getEncoding() */
  private String encoding;

  /**
   * The constructor.
   */
  public DirectorySearchIndexer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param indexer is the indexer to use.
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
   * @param indexer the indexer to set
   */
  @Resource
  public void setIndexer(SearchIndexer indexer) {

    this.indexer = indexer;
  }

  /**
   * This method gets the filter that decides which directories to scan and
   * which files to index.<br>
   * 
   * @return the filter
   */
  public FileFilter getFilter() {

    return this.filter;
  }

  /**
   * This method sets the {@link #getFilter() file-filter}.<br>
   * 
   * @see net.sf.mmm.util.filter.FilterRuleChainXmlParser
   * @see net.sf.mmm.util.filter.FilterRuleChainPlainParser
   * @see net.sf.mmm.util.filter.FileFilterAdapter
   * 
   * @param filter the filter to set
   */
  @Resource
  public void setFilter(FileFilter filter) {

    this.filter = filter;
  }

  /**
   * This method gets the optional rewriter for the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() URI}s. This allows to
   * rewrite the path of files indexed from the local filesystem if it differs
   * from the path to access the file online.
   * 
   * @return the URI-rewriter or <code>null</code> if URIs should NOT be
   *         rewritten.
   */
  public Transformer<String> getUriRewriter() {

    return this.uriRewriter;
  }

  /**
   * This method sets the {@link #getUriRewriter() URI-rewriter}.
   * 
   * @param urlRewriter the URI-rewriter to set.
   */
  public void setUriRewriter(Transformer<String> urlRewriter) {

    this.uriRewriter = urlRewriter;
  }

  /**
   * @return the logger
   */
  public Log getLogger() {

    return this.logger;
  }

  /**
   * @param logger the logger to set
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
   * @param parserService the parserService to set
   */
  @Resource
  public void setParserService(ContentParserService parserService) {

    this.parserService = parserService;
  }

  /**
   * This method gets the encoding.
   * 
   * @return the explicit encoding to use for textual files or <code>null</code>
   *         for smart unicode detection.
   */
  public String getEncoding() {

    return this.encoding;
  }

  /**
   * This method sets the {@link #getEncoding() encoding}.
   * 
   * @param encoding the encoding to set.
   */
  public void setEncoding(String encoding) {

    this.encoding = encoding;
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
    if (this.indexer == null) {
      throw new ResourceMissingException("indexer");
    }
  }

  /**
   * This method starts the indexing from the given <code>directory</code>.
   * 
   * @see #indexDirectory(String, File)
   * 
   * @param directory is the directory to index recursively.
   */
  public void indexDirectory(File directory) {

    indexDirectory(null, directory);
  }

  /**
   * This method starts the indexing from the given <code>directory</code>
   * adding the given <code>source</code> as metadata.
   * 
   * @param source is the
   *        {@link net.sf.mmm.search.api.SearchEntry#getSource() source}
   *        attribute of the indexed entries.
   * @param directory is the directory to index recursively.
   */
  public void indexDirectory(String source, File directory) {

    indexDirectory(source, directory, "");
  }

  /**
   * This method starts the indexing from the given <code>directory</code>
   * adding the given <code>source</code> as metadata.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   * 
   * @param source is the
   *        {@link net.sf.mmm.search.api.SearchEntry#getSource() source}
   *        attribute of the indexed entries.
   * @param directory is the directory to index recursively.
   * @param relativePath is the base path where used to build the
   *        {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of the
   *        indexed entries. E.g. when <code>source</code> is
   *        <code>"svn"</code> and <code>directory</code> points to where
   *        you checked out a subversion repository then
   *        <code>relativePath</code> may be <code>"trunk"</code>. This is
   *        especially useful, when you index multiple sub-directories from the
   *        same <code>source</code>. You could also use
   *        <code>"http://svn.foo.bar/trunk"</code> as
   *        <code>relativePath</code> but this would cause a lot of
   *        unnecessary redundancies in your index. Besides you would rather
   *        search for <code>source:svn</code> than
   *        <code>uri:http://svn.foo.bar/trunk/*</code>.
   */
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
   * This method indexes a single file.
   * 
   * @param source is the
   *        {@link net.sf.mmm.search.api.SearchEntry#getSource() source}
   *        attribute of the indexed entry.
   * @param file is the file to index.
   * @param relativePath is the path of the folder where the file is located
   *        relative to the path given when the indexing was started. This is
   *        used to build the
   *        {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of the file
   *        in the search index.
   */
  public void indexFile(String source, File file, String relativePath) {

    this.logger.debug("Indexing " + file.getPath());
    String filename = file.getName();
    String fullPath = relativePath + "/" + filename;
    long fileSize = file.length();
    String extension = FileUtil.getExtension(filename);
    MutableSearchEntry entry = this.indexer.createEntry();
    String uri = fullPath;
    if (this.uriRewriter != null) {
      uri = this.uriRewriter.transform(uri);
    }
    entry.setUri(uri);
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
          Properties properties = parser.parse(inputStream, fileSize, this.encoding);
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
