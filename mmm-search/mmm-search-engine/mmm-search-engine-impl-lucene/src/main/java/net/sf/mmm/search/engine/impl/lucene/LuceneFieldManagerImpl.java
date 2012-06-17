/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchConfigurationHolder;
import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.api.config.SearchFieldMode;
import net.sf.mmm.search.api.config.SearchFieldType;
import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.NumericUtils;

/**
 * This class centralizes the behavior of fields according to their {@link SearchFieldConfiguration} for
 * lucene.
 * 
 * @see Field
 * @see NumericField
 * @see Term
 * @see NumericRangeQuery
 * @see TermRangeQuery
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneFieldManagerImpl extends AbstractLoggableObject implements LuceneFieldManager {

  /** The {@link Iso8601Util} for date fields. */
  private final Iso8601Util iso8601Util;

  /** @see #getConfigurationHolder() */
  private final SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder;

  /** The {@link Analyzer} */
  private final Analyzer analyzer;

  /** @see #getPrecisionStep() */
  private Integer precisionStep;

  /**
   * The constructor.
   * 
   * @param configurationHolder is the {@link SearchConfigurationHolder}.
   * @param analyzer the {@link Analyzer}.
   * @param iso8601Util is the {@link Iso8601Util} instance used to handle {@link Date} fields.
   */
  public LuceneFieldManagerImpl(SearchConfigurationHolder<? extends SearchConfiguration> configurationHolder,
      Analyzer analyzer, Iso8601Util iso8601Util) {

    super();
    this.configurationHolder = configurationHolder;
    this.analyzer = analyzer;
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchConfigurationHolder<? extends SearchConfiguration> getConfigurationHolder() {

    return this.configurationHolder;
  }

  /**
   * This method gets the {@link SearchFields}.
   * 
   * @return the {@link SearchFields}.
   */
  protected SearchFields getSearchFields() {

    return this.configurationHolder.getBean().getFields();
  }

  /**
   * @return the precisionStep
   */
  @Override
  public int getPrecisionStep() {

    Integer result = this.precisionStep;
    if (result == null) {
      int step = NumericUtils.PRECISION_STEP_DEFAULT;
      String precision = this.configurationHolder.getBean().getProperties().getProperty(PROPERTY_PRECISION_STEP);
      if (precision != null) {
        try {
          step = Integer.parseInt(precision);
        } catch (NumberFormatException e) {
          getLogger().warn("Ignoring illegal value of property " + PROPERTY_PRECISION_STEP, e);
        }
      }
      result = Integer.valueOf(step);
      this.precisionStep = result;
    }
    return result.intValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("null")
  public Fieldable createField(String field, Object value) {

    NlsNullPointerException.checkNotNull("field", field);
    NlsNullPointerException.checkNotNull("value", value);
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getOrCreateFieldConfiguration(field);
    SearchFieldType fieldType = fieldConfiguration.getType();
    SearchFieldMode fieldMode = fieldConfiguration.getMode();
    Store store;
    if (fieldMode.isRetrievable()) {
      store = Store.YES;
    } else {
      store = Store.NO;
    }
    Fieldable result;
    NumericField numericField = null;
    if ((fieldType != SearchFieldType.TEXT) && ((fieldType != SearchFieldType.STRING))) {
      numericField = new NumericField(field, getPrecisionStep(), store, fieldMode.isSearchable());
    }
    try {
      switch (fieldType) {
        case TEXT:
          if (value instanceof Reader) {
            if (fieldMode != SearchFieldMode.SEARCHABLE) {
              throw new NlsIllegalStateException();
            }
            result = new Field(field, (Reader) value);
          } else {
            Index index;
            if (fieldMode.isSearchable()) {
              index = Index.ANALYZED;
            } else {
              index = Index.NO;
            }
            result = new Field(field, (String) value, store, index);
          }
          break;
        case STRING:
          Index index;
          if (fieldMode.isSearchable()) {
            index = Index.NOT_ANALYZED;
          } else {
            index = Index.NO;
          }
          result = new Field(field, (String) value, store, index);
          break;
        case INTEGER:
          Integer i = (Integer) value;
          numericField.setIntValue(i.intValue());
          result = numericField;
          break;
        case LONG:
          Long l = (Long) value;
          numericField.setLongValue(l.longValue());
          result = numericField;
          break;
        case FLOAT:
          Float f = (Float) value;
          numericField.setFloatValue(f.floatValue());
          result = numericField;
          break;
        case DOUBLE:
          Double d = (Double) value;
          numericField.setDoubleValue(d.doubleValue());
          result = numericField;
          break;
        case DATE:
          Date date = (Date) value;
          numericField.setLongValue(date.getTime());
          result = numericField;
          break;
        default :
          throw new IllegalCaseException(SearchFieldType.class, fieldType);
      }
    } catch (ClassCastException e) {
      // TODO: Throw exception explaining the SearchFields configuration
      throw new NlsClassCastException(e, value, fieldType.getFieldClass());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Term createTerm(String field, Object value) {

    NlsNullPointerException.checkNotNull("field", field);
    NlsNullPointerException.checkNotNull("value", value);
    String normalizedValue;
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getOrCreateFieldConfiguration(field);
    SearchFieldType fieldType = fieldConfiguration.getType();
    boolean isString = (value instanceof String);
    try {
      switch (fieldType) {
        case TEXT:
          try {
            TokenStream tokenStream = this.analyzer.tokenStream(field, new StringReader((String) value));
            TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
            if (tokenStream.incrementToken()) {
              normalizedValue = termAttribute.term();
            } else {
              normalizedValue = "";
            }
          } catch (IOException e) {
            throw new RuntimeIoException(e, IoMode.READ);
          }
          break;
        case STRING:
          normalizedValue = (String) value;
          break;
        case INTEGER:
          int i;
          if (isString) {
            i = Integer.parseInt((String) value);
          } else {
            i = ((Integer) value).intValue();
          }
          normalizedValue = NumericUtils.intToPrefixCoded(i);
          break;
        case LONG:
          long l;
          if (isString) {
            l = Long.parseLong((String) value);
          } else {
            l = ((Long) value).longValue();
          }
          normalizedValue = NumericUtils.longToPrefixCoded(l);
          break;
        case FLOAT:
          float f;
          if (isString) {
            f = Float.parseFloat((String) value);
          } else {
            f = ((Float) value).floatValue();
          }
          normalizedValue = NumericUtils.floatToPrefixCoded(f);
          break;
        case DOUBLE:
          double d;
          if (isString) {
            d = Double.parseDouble((String) value);
          } else {
            d = ((Double) value).doubleValue();
          }
          normalizedValue = NumericUtils.doubleToPrefixCoded(d);
          break;
        case DATE:
          Date date;
          if (isString) {
            date = this.iso8601Util.parseDate((String) value);
          } else {
            date = (Date) value;
          }
          normalizedValue = NumericUtils.longToPrefixCoded(date.getTime());
          break;
        default :
          throw new IllegalCaseException(SearchFieldType.class, fieldType);
      }
    } catch (ClassCastException e) {
      throw new NlsClassCastException(e, value, fieldType.getFieldClass());
    }
    return new Term(field, normalizedValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createPhraseQuery(String field, String value) {

    NlsNullPointerException.checkNotNull("field", field);
    NlsNullPointerException.checkNotNull("value", value);
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getOrCreateFieldConfiguration(field);
    SearchFieldType fieldType = fieldConfiguration.getType();
    Query result;
    if (fieldType == SearchFieldType.TEXT) {
      PhraseQuery phraseQuery = new PhraseQuery();
      result = phraseQuery;
      try {
        TokenStream tokenStream = this.analyzer.tokenStream(field, new StringReader(value));
        TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
        while (tokenStream.incrementToken()) {
          phraseQuery.add(new Term(field, termAttribute.term()));
        }
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.READ);
      }
    } else {
      result = new TermQuery(createTerm(field, value));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Query createRangeQuery(String field, String minimum, String maximum, boolean minimumInclusive,
      boolean maximumInclusive) {

    NlsNullPointerException.checkNotNull("field", field);
    NlsNullPointerException.checkNotNull("minimum", minimum);
    NlsNullPointerException.checkNotNull("maximum", maximum);
    SearchFieldConfiguration fieldConfiguration = getSearchFields().getOrCreateFieldConfiguration(field);
    SearchFieldType fieldType = fieldConfiguration.getType();
    Query result;
    switch (fieldType) {
      case TEXT:
        result = new TermRangeQuery(field, minimum, maximum, minimumInclusive, maximumInclusive);
        break;
      case STRING:
        result = new TermRangeQuery(field, minimum, maximum, minimumInclusive, maximumInclusive);
        break;
      case INTEGER:
        result = NumericRangeQuery.newIntRange(field, getPrecisionStep(), Integer.valueOf(minimum),
            Integer.valueOf(maximum), minimumInclusive, maximumInclusive);
        break;
      case LONG:
        result = NumericRangeQuery.newLongRange(field, getPrecisionStep(), Long.valueOf(minimum),
            Long.valueOf(maximum), minimumInclusive, maximumInclusive);
        break;
      case FLOAT:
        result = NumericRangeQuery.newFloatRange(field, getPrecisionStep(), Float.valueOf(minimum),
            Float.valueOf(maximum), minimumInclusive, maximumInclusive);
        break;
      case DOUBLE:
        result = NumericRangeQuery.newDoubleRange(field, getPrecisionStep(), Double.valueOf(minimum),
            Double.valueOf(maximum), minimumInclusive, maximumInclusive);
        break;
      case DATE:
        Date minDate = this.iso8601Util.parseDate(minimum);
        Date maxDate = this.iso8601Util.parseDate(maximum);
        result = NumericRangeQuery.newLongRange(field, getPrecisionStep(), Long.valueOf(minDate.getTime()),
            Long.valueOf(maxDate.getTime()), minimumInclusive, maximumInclusive);
        break;
      default :
        throw new IllegalCaseException(SearchFieldType.class, fieldType);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean refresh() {

    this.precisionStep = null;
    return this.configurationHolder.refresh();
  }

}
