/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import java.io.IOException;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchEntryIdInvalidException;
import net.sf.mmm.search.base.SearchPropertyValueInvalidException;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.impl.lucene.LuceneSearchEngineBuilder;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexer;
import net.sf.mmm.search.indexer.base.SearchAddFailedException;
import net.sf.mmm.search.indexer.base.SearchRemoveFailedException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexer extends AbstractSearchIndexer {

  /** the {@link IndexWriter}. */
  private final IndexWriter indexWriter;

  /** @see #getSearchEngine() */
  private ManagedSearchEngine searchEngine;

  /** @see #getSearchEngine() */
  private LuceneSearchEngineBuilder searchEngineBuilder;

  /**
   * The constructor.
   * 
   * @param indexWriter is the index modifier to use.
   * @param searchEngineBuilder is the {@link LuceneSearchEngineBuilder}
   *        required for {@link #getSearchEngine()}.
   */
  public LuceneSearchIndexer(IndexWriter indexWriter, LuceneSearchEngineBuilder searchEngineBuilder) {

    super();
    this.indexWriter = indexWriter;
    this.searchEngineBuilder = searchEngineBuilder;
  }

  /**
   * {@inheritDoc}
   */
  public void add(MutableSearchEntry entry) throws SearchException {

    try {
      LuceneMutableSearchEntry luceneEntry = (LuceneMutableSearchEntry) entry;
      Document luceneDocument = luceneEntry.getLuceneDocument();
      this.indexWriter.addDocument(luceneDocument);
    } catch (IOException e) {
      throw new SearchAddFailedException(e, entry);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    try {
      this.indexWriter.close();
      if (this.searchEngine != null) {
        this.searchEngine.close();
        this.searchEngine = null;
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void flush() throws SearchException {

    try {
      this.indexWriter.commit();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  public MutableSearchEntry createEntry() {

    return new LuceneMutableSearchEntry(new Document());
  }

  /**
   * {@inheritDoc}
   */
  public void optimize() throws SearchException {

    try {
      this.indexWriter.optimize();
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeById(String entryId) throws SearchException {

    try {
      int docId = Integer.parseInt(entryId);
      // lucene is strange: IndexReader is required to delete documents by id
      // according to this issue:
      // https://issues.apache.org/jira/browse/LUCENE-1721
      // the documentId is no ID at all as it may turn invalid if the index
      // changed.
      // lucene does NOT seem to give use feedback here...
      // TODO: javadoc: getReader() is read-only, so this code buggy
      this.indexWriter.getReader().deleteDocument(docId);
      return true;
    } catch (NumberFormatException e) {
      throw new SearchEntryIdInvalidException(e, entryId);
    } catch (IOException e) {
      throw new SearchRemoveFailedException("ID", entryId);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUid(String uid) throws SearchException {

    return remove(SearchEntry.PROPERTY_UID, uid);
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUri(String uri) throws SearchException {

    return remove(SearchEntry.PROPERTY_URI, uri);
  }

  /**
   * {@inheritDoc}
   */
  public int remove(String property, String value) throws SearchException {

    if ((value == null) || (value.length() == 0)) {
      throw new SearchPropertyValueInvalidException(property, value);
    }
    try {
      this.indexWriter.deleteDocuments(new Term(property, value));
      return 0;
    } catch (IOException e) {
      throw new SearchRemoveFailedException(property, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchEngine getSearchEngine() {

    try {
      if (this.searchEngine == null) {
        this.searchEngine = this.searchEngineBuilder.createSearchEngine(this.indexWriter
            .getReader());
      }
      return this.searchEngine;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

}
