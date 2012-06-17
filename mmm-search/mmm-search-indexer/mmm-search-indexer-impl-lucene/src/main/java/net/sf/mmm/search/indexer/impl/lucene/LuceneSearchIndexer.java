/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import java.io.IOException;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.base.SearchPropertyValueInvalidException;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.impl.lucene.LuceneFieldManager;
import net.sf.mmm.search.engine.impl.lucene.LuceneSearchEngineBuilder;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexer;
import net.sf.mmm.search.indexer.base.SearchAddFailedException;
import net.sf.mmm.search.indexer.base.SearchRemoveFailedException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.NumericUtils;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexer extends AbstractSearchIndexer {

  /** @see #getSearchEngine() */
  private final LuceneSearchEngineBuilder searchEngineBuilder;

  /** The {@link LuceneFieldManager}. */
  private final LuceneFieldManager fieldManager;

  /**
   * The {@link SearchDependencies}.
   * 
   * @see #createEntry()
   */
  private final SearchDependencies searchDependencies;

  /**
   * The base-ID for this session. It is combined with {@link #addCounter} to
   * create {@link SearchEntry#getId() IDs}.
   */
  private final long sessionId;

  /**
   * The number of {@link SearchEntry entries} {@link #add(MutableSearchEntry)
   * added} in this session.
   */
  private int addCounter;

  /** The {@link IndexWriter}. */
  private IndexWriter indexWriter;

  /** @see #getSearchEngine() */
  private ManagedSearchEngine searchEngine;

  /**
   * The constructor.
   * 
   * @param indexWriter is the index modifier to use.
   * @param searchEngineBuilder is the {@link LuceneSearchEngineBuilder}
   *        required for {@link #getSearchEngine()}.
   * @param fieldManager is the {@link LuceneFieldManager}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public LuceneSearchIndexer(IndexWriter indexWriter,
      LuceneSearchEngineBuilder searchEngineBuilder, LuceneFieldManager fieldManager,
      SearchDependencies searchDependencies) {

    super();
    this.searchEngineBuilder = searchEngineBuilder;
    this.fieldManager = fieldManager;
    this.searchDependencies = searchDependencies;
    this.sessionId = System.currentTimeMillis() << 24;
    this.indexWriter = indexWriter;
    this.addCounter = 0;
  }

  /**
   * This method gets the {@link IndexWriter}.
   * 
   * @return the {@link IndexWriter}.
   */
  protected IndexWriter getIndexWriter() {

    if (this.indexWriter == null) {
      // already closed...
      throw new NlsIllegalStateException();
    }
    return this.indexWriter;
  }

  /**
   * {@inheritDoc}
   */
  public void add(MutableSearchEntry entry) throws SearchException {

    NlsNullPointerException.checkNotNull(MutableSearchEntry.class, entry);
    this.addCounter++;
    Long id = Long.valueOf(this.sessionId | this.addCounter);
    add(entry, id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void add(MutableSearchEntry entry, Long id) throws SearchException {

    LuceneMutableSearchEntry luceneEntry = (LuceneMutableSearchEntry) entry;
    try {
      Document luceneDocument = luceneEntry.getLuceneDocument();
      NumericField idField = new NumericField(SearchEntry.FIELD_ID,
          NumericUtils.PRECISION_STEP_DEFAULT, Store.YES, true);
      idField.setLongValue(id.longValue());
      luceneDocument.add(idField);
      getIndexWriter().addDocument(luceneDocument);
    } catch (IOException e) {
      throw new SearchAddFailedException(e, entry);
    } finally {
      luceneEntry.dispose();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    try {
      if (this.indexWriter != null) {
        this.indexWriter.close();
        this.indexWriter = null;
      }
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
      if (this.indexWriter != null) {
        this.indexWriter.commit();
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.FLUSH);
    }
  }

  /**
   * {@inheritDoc}
   */
  public MutableSearchEntry createEntry() {

    return new LuceneMutableSearchEntry(new Document(), this.fieldManager, this.searchDependencies);
  }

  /**
   * {@inheritDoc}
   */
  public void optimize() throws SearchException {

    try {
      getIndexWriter().optimize();
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * This method performs the actual remove by {@link Term}.
   * 
   * @see #removeByCustumId(String)
   * @see #removeByUri(String, String)
   * @see IndexWriter#deleteDocuments(Term)
   * 
   * @param term is the {@link Term} identifying the {@link SearchEntry
   *        entry/entries} to remove.
   * @return the number of removed {@link SearchEntry entries}.
   */
  protected int remove(Term term) {

    try {
      IndexWriter writer = getIndexWriter();
      int count = writer.getReader().docFreq(term);
      if (count > 0) {
        writer.deleteDocuments(term);
      }
      return count;
    } catch (IOException e) {
      throw new SearchRemoveFailedException(e, term.field(), term.text());
    }
  }

  /**
   * {@inheritDoc}
   */
  public int remove(String field, Object value) throws SearchException {

    if (value == null) {
      throw new SearchPropertyValueInvalidException(field, "null");
    }
    Term term = this.fieldManager.createTerm(field, value);
    if (term.text().length() == 0) {
      throw new SearchPropertyValueInvalidException(field, value.toString());
    }
    return remove(term);
  }

  /**
   * {@inheritDoc}
   */
  public SearchEngine getSearchEngine() {

    try {
      if (this.searchEngine == null) {
        this.searchEngine = this.searchEngineBuilder.createSearchEngine(
            this.indexWriter.getReader(), this.fieldManager.getConfigurationHolder(), null);
      }
      return this.searchEngine;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

}
