/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.engine.impl.lucene.LuceneFieldManager;
import net.sf.mmm.search.impl.lucene.LuceneFieldNameIterator;
import net.sf.mmm.search.indexer.base.AbstractMutableSearchEntry;
import net.sf.mmm.search.indexer.base.SearchEntryIdImmutableException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.MutableSearchEntry} interface using
 * lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneMutableSearchEntry extends AbstractMutableSearchEntry {

  /** The lucene document that represents the actual entry. */
  private final Document document;

  /** The {@link LuceneFieldManager}. */
  private final LuceneFieldManager fieldManager;

  /** @see #dispose() */
  private List<Reader> readers;

  /**
   * The constructor.
   * 
   * @param document is the lucene document that represents the actual entry.
   * @param fieldManager is the {@link LuceneFieldManager}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public LuceneMutableSearchEntry(Document document, LuceneFieldManager fieldManager,
      SearchDependencies searchDependencies) {

    super(fieldManager.getConfigurationHolder().getBean().getFields(), searchDependencies);
    this.fieldManager = fieldManager;
    this.document = document;
  }

  /**
   * This method gets the unwrapped lucene document.
   * 
   * @return the lucene document.
   */
  public Document getLuceneDocument() {

    return this.document;
  }

  /**
   * {@inheritDoc}
   */
  public void setBoost(double boost) {

    this.document.setBoost((float) boost);
  }

  /**
   * {@inheritDoc}
   */
  public void setField(String name, Object value) {

    NlsNullPointerException.checkNotNull("name", name);
    NlsNullPointerException.checkNotNull("value", value);
    if (FIELD_ID.equals(name)) {
      throw new SearchEntryIdImmutableException();
    }
    if (value instanceof Reader) {
      if (this.readers == null) {
        this.readers = new ArrayList<Reader>();
      }
      this.readers.add((Reader) value);
    }
    Fieldable field = this.fieldManager.createField(name, value);
    this.document.add(field);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getFieldRaw(String name) {

    Fieldable fieldable = this.document.getFieldable(name);
    if (fieldable == null) {
      return null;
    }
    if (fieldable instanceof NumericField) {
      return ((NumericField) fieldable).getNumericValue();
    }
    return fieldable.stringValue();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<String> getFieldNames() {

    return new LuceneFieldNameIterator(this.document);
  }

  /**
   * This method is called to dispose this entry.
   */
  public void dispose() {

    if (this.readers != null) {
      Exception exception = null;
      for (Reader reader : this.readers) {
        try {
          reader.close();
        } catch (Exception e) {
          exception = e;
        }
      }
      if (exception != null) {
        throw new RuntimeIoException(exception, IoMode.CLOSE);
      }
    }
  }

}
