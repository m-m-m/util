/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexModifier;
import org.apache.lucene.index.Term;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchAddFailedException;
import net.sf.mmm.search.base.SearchIdInvalidException;
import net.sf.mmm.search.base.SearchIoException;
import net.sf.mmm.search.base.SearchPropertyValueInvalidException;
import net.sf.mmm.search.base.SearchRemoveFailedException;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexer;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexer extends AbstractSearchIndexer {

  /** the index modifier */
  private final IndexModifier modifier;

  /**
   * The constructor. 
   * 
   * @param indexModifier
   *        is the index modifier to use.
   */
  public LuceneSearchIndexer(IndexModifier indexModifier) {

    super();
    this.modifier = indexModifier;
  }

  /**
   * {@inheritDoc}
   */
  public void add(MutableSearchEntry entry) throws SearchException {

    try {
      LuceneMutableSearchEntry luceneEntry = (LuceneMutableSearchEntry) entry;
      Document luceneDocument = luceneEntry.getLuceneDocument();
      this.modifier.addDocument(luceneDocument);
    } catch (IOException e) {
      throw new SearchAddFailedException(e, entry);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    try {
      this.modifier.close();
    } catch (IOException e) {
      throw new SearchIoException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void flush() throws SearchException {

    try {
      this.modifier.flush();
    } catch (IOException e) {
      throw new SearchIoException(e);
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
      this.modifier.optimize();
    } catch (IOException e) {
      throw new SearchIoException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeById(String entryId) throws SearchException {

    try {
      int docId = Integer.parseInt(entryId);
      // lucene does NOT seem to give use feedback here...
      this.modifier.deleteDocument(docId);
      return true;
    } catch (NumberFormatException e) {
      throw new SearchIdInvalidException(e, entryId);
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
      return this.modifier.deleteDocuments(new Term(property, value));
    } catch (IOException e) {
      throw new SearchRemoveFailedException(property, value);
    }
  }

}
