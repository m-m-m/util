/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import java.util.Iterator;

import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.search.base.BasicSearchEntry;
import net.sf.mmm.search.base.SearchDependencies;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;

/**
 * This is the implementation of the {@link net.sf.mmm.search.api.SearchEntry}
 * interface using lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEntry extends BasicSearchEntry {

  /** the lucene document that represents the actual entry */
  private final Document document;

  /**
   * The constructor.
   * 
   * @param document is the lucene document that represents the actual entry.
   * @param searchFields are the {@link SearchFields}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public LuceneSearchEntry(Document document, SearchFields searchFields,
      SearchDependencies searchDependencies) {

    super(searchFields, searchDependencies);
    this.document = document;
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
    // actually lucene will always return a regular field, but this might change
    if (fieldable instanceof NumericField) {
      return ((NumericField) fieldable).getNumericValue();
    }
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getFieldConfiguration(name);
    if (fieldConfiguration != null) {
      if (fieldConfiguration.getType() == SearchFieldType.DATE) {
        // we do not want to convert from string to string on higher level!
        return Long.valueOf(fieldable.stringValue());
      }
    }
    return fieldable.stringValue();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<String> getFieldNames() {

    return new LuceneFieldNameIterator(this.document);
  }

}
