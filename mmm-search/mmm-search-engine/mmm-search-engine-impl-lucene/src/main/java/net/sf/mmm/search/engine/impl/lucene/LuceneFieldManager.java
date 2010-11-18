/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.util.component.api.Refreshable;

import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

/**
 * This is the interface for a manager that centralizes the behavior of fields
 * according to their
 * {@link net.sf.mmm.search.api.config.SearchFieldConfiguration} for lucene.
 * 
 * @see org.apache.lucene.document.Field
 * @see org.apache.lucene.document.NumericField
 * @see Term
 * @see org.apache.lucene.search.NumericRangeQuery
 * @see org.apache.lucene.search.TermRangeQuery
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface LuceneFieldManager extends Refreshable {

  /**
   * The name of the
   * {@link net.sf.mmm.search.api.config.SearchProperties#getProperty(String)
   * property} for {@link LuceneFieldManagerImpl#getPrecisionStep()}.
   */
  String PROPERTY_PRECISION_STEP = "net.sf.mmm."
      + "search.engine.impl.lucene.LuceneFieldManager.precisionStep";

  /**
   * @return the precisionStep
   */
  int getPrecisionStep();

  /**
   * This method creates the {@link Fieldable field} for the given
   * <code>field</code> (name) and <code>value</code>.
   * 
   * @param field is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field} to create.
   * @param value is the value of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field} to create.
   * @return the created field.
   */
  Fieldable createField(String field, Object value);

  /**
   * This method creates a {@link Term} for the given <code>field</code> with
   * the given <code>value</code>.
   * 
   * @param field is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field(s)} to match.
   * @param value is the expected value of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field(s)} to match. Please note that this value is a single term
   *        (word) and not text.
   * @return the created {@link Term}.
   */
  Term createTerm(String field, Object value);

  /**
   * This method creates a phrase query.
   * 
   * @param field is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field(s)} to match.
   * @param value is the expected phrase of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field(s)} to match.
   * @return the created {@link Query}.
   */
  Query createPhraseQuery(String field, String value);

  /**
   * This method create a range {@link Query}.
   * 
   * @see org.apache.lucene.search.NumericRangeQuery
   * @see org.apache.lucene.search.TermRangeQuery
   * 
   * @param field is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String)
   *        field(s)} to match.
   * @param minimum is the minimum or infimum value.
   * @param maximum is the maximum or supremum value.
   * @param minimumInclusive - <code>true</code> if the <code>minimum</code> is
   *        included and matches, <code>false</code> if the <code>minimum</code>
   *        is treated as infimum and only higher values will match.
   * @param maximumInclusive - <code>true</code> if the <code>maximum</code> is
   *        included and matches, <code>false</code> if the <code>maximum</code>
   *        is treated as supremum and only lower values will match.
   * @return the created range {@link Query}.
   */
  Query createRangeQuery(String field, String minimum, String maximum, boolean minimumInclusive,
      boolean maximumInclusive);

  /**
   * This method gets the {@link SearchConfigurationHolder}.
   * 
   * @return the {@link SearchConfigurationHolder}.
   */
  SearchConfigurationHolder<? extends SearchConfiguration> getConfigurationHolder();

  /**
   * Will update {@link #getPrecisionStep()} but not refresh
   * {@link #getConfigurationHolder()}.
   * 
   * {@inheritDoc}
   */
  boolean refresh();

}
