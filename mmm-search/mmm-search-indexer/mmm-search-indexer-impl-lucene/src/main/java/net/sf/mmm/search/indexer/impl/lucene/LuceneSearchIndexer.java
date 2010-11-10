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
   * This method performs the actual remove by {@link Term}.
   * 
   * @see #removeByUid(String)
   * @see #removeByUri(String, String)
   * @see IndexWriter#deleteDocuments(Term)
   * 
   * @param terms are the {@link Term}s identifying the {@link SearchEntry
   *        entries} to remove.
   * @return the number of removed {@link SearchEntry entries}.
   */
  protected int remove(Term... terms) {

    try {
      this.indexWriter.deleteDocuments(terms);
      return -1;
    } catch (IOException e) {
      throw new SearchRemoveFailedException(e, terms[0].field(), terms[0].text());
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
      // the documentId might also not be an ID at all as it may turn invalid
      // if the index changed.
      // lucene does NOT seem to give feedback here...

      // TODO: javadoc: getReader() is read-only, so this code buggy
      this.indexWriter.getReader().deleteDocument(docId);
      return true;
    } catch (NumberFormatException e) {
      throw new SearchEntryIdInvalidException(e, entryId);
    } catch (IOException e) {
      throw new SearchRemoveFailedException(e, "ID", entryId);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int remove(String property, String value) throws SearchException {

    if ((value == null) || (value.length() == 0)) {
      throw new SearchPropertyValueInvalidException(property, value);
    }
    return remove(new Term(property, value));
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUri(String uri, String sourceId) throws SearchException {

    if (sourceId == null) {
      return remove(SearchEntry.PROPERTY_URI, uri);
    } else {
      if ((uri == null) || (uri.length() == 0)) {
        throw new SearchPropertyValueInvalidException(SearchEntry.PROPERTY_URI, uri);
      }
      return remove(new Term(SearchEntry.PROPERTY_URI, uri), new Term(SearchEntry.PROPERTY_SOURCE,
          sourceId));
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
