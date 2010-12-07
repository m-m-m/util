/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchQueryBuilderOptions;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.nls.api.NlsRuntimeException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.text.api.UnicodeUtil;

/**
 * This is the abstract base implementation of the {@link SearchQueryBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchQueryBuilder extends AbstractLoggableObject implements
    SearchQueryBuilder {

  /** The {@link CharFilter} to match anything except the end of a range query. */
  private static final CharFilter CHAR_FILTER_ACCEPT_UNTIL_END_OF_RANGE = new ListCharFilter(false,
      '}', ']');

  /**
   * The separator of minimum and maximum in a range query. Has to be separated
   * with whitespaces.
   */
  private static final Set<String> RANGE_QUERY_SEPARATOR_SET;
  static {
    Set<String> set = new HashSet<String>();
    set.add("");
    set.add("-");
    set.add("TO");
    set.add("to");
    set.add("To");
    set.add(Character.toString(UnicodeUtil.EN_DASH));
    set.add(Character.toString(UnicodeUtil.EM_DASH));
    set.add(Character.toString(UnicodeUtil.FIGURE_DASH));
    set.add(Character.toString(UnicodeUtil.MINUS_SIGN));
    set.add(Character.toString(UnicodeUtil.HYPHEN));
    set.add(",");
    RANGE_QUERY_SEPARATOR_SET = set;
  }

  /**
   * The constructor.
   */
  public AbstractSearchQueryBuilder() {

    super();
  }

  /**
   * This method creates (or gets) a query that matches anything.
   * 
   * @return a query that matches any {@link SearchEntry entry}.
   */
  protected SearchQuery createNullQuery() {

    return createWordQuery(SearchEntry.FIELD_TEXT, "*");
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery parseStandardQuery(String query) throws SearchException {

    return parseStandardQuery(query, new SearchQueryBuilderOptionsBean());
  }

  /**
   * @see #parseStandardQuery(String, SearchQueryBuilderOptions)
   * 
   * @param parser is the scanner of the query-string.
   * @param options are the {@link SearchQueryBuilderOptions}.
   * @param defaultProperty is the property to use as default for unqualified
   *        search-terms.
   * @param depth is the depth of the query expression (number of open
   *        parenthesis).
   * @return the parsed query or <code>null</code> if this a call with a depth
   *         greater than <code>0</code> and the parsed query segment was void.
   */
  private SearchQuery parseStandardQuery(CharSequenceScanner parser,
      SearchQueryBuilderOptions options, String defaultProperty, int depth) {

    ComplexSearchQuery complexQuery = createComplexQuery();
    SearchQuery subQuery = null;
    boolean todo = true;
    while (todo) {
      // determine conjunction...
      parser.skipWhile(CharFilter.WHITESPACE_FILTER);
      char conjunction = 0;
      while (parser.hasNext()) {
        char c = parser.peek();
        if ((c == '+') || (c == '-')) {
          parser.next();
          conjunction = c;
        } else {
          break;
        }
      }
      subQuery = parseStandardClause(parser, defaultProperty, depth, options);
      if (subQuery != null) {
        if (conjunction == '+') {
          complexQuery.addRequiredQuery(subQuery);
        } else if (conjunction == '-') {
          complexQuery.addExcludingQuery(subQuery);
        } else if (options.isRequireTerms() && (depth == 0)) {
          complexQuery.addRequiredQuery(subQuery);
        } else {
          complexQuery.addOptionalQuery(subQuery);
        }
      }
      // TODO if no closing ) we should call errorHandler
      if (parser.hasNext()) {
        if (parser.peek() == ')') {
          parser.next();
          if (depth > 0) {
            todo = false;
          }
        }
      } else {
        todo = false;
      }
    }
    int size = complexQuery.getSubQueryCount();
    if (size == 0) {
      if (depth == 0) {
        return createNullQuery();
      } else {
        return null;
      }
      // } else if (size == 1) {
      // return subQuery;
    } else {
      return complexQuery;
    }
  }

  /**
   * 
   * @param parser is the scanner of the query-string.
   * @param defaultField is the field to use as default for unqualified
   *        search-terms.
   * @param depth is the depth of the query expression (number of open
   *        parenthesis).
   * @param options are the {@link SearchQueryBuilderOptions}.
   * @return the parsed query or <code>null</code> if the parsed query segment
   *         was void.
   */
  private SearchQuery parseStandardClause(CharSequenceScanner parser, String defaultField,
      int depth, SearchQueryBuilderOptions options) {

    char c;
    String fieldName = defaultField;
    int start = parser.getCurrentIndex();
    String ascii = parser.readWhile(CharFilter.ASCII_LETTER_FILTER);
    if (ascii.length() > 0) {
      c = parser.forceNext();
      if (c == ':') {
        fieldName = ascii;
        start = parser.getCurrentIndex();
      } else {
        // lookahead was negative, step back...
        parser.setCurrentIndex(start);
      }
    }
    try {
      if (parser.hasNext()) {
        c = parser.peek();
        if ((c == '"') || (c == '\'')) {
          // phrase query
          parser.next();
          String phrase = parser.readUntil(c, true);
          return createPhraseQuery(fieldName, phrase);
        } else if ((c == '[') || (c == '{')) {
          parser.next();
          // range query
          // [123 - 789]
          // [mona-lisa TO peter-pan}
          // {0.24618 , 0.999}
          // [2010-09-02T23:59:59Z - 2010-09-04T22:35:00Z]
          boolean minimumInclusive = (c == '[');
          String minimum = parser.readUntil(' ', false);
          if (minimum != null) {
            parser.skipWhile(' ');
            String to = parser.readUntil(' ', false);
            if (to != null) {
              parser.skipWhile(' ');
              if (RANGE_QUERY_SEPARATOR_SET.contains(to)) {
                String maximum = parser.readWhile(CHAR_FILTER_ACCEPT_UNTIL_END_OF_RANGE);
                if (parser.hasNext()) {
                  c = parser.next();
                  boolean maximumInclusive = (c == ']');
                  return createRangeQuery(fieldName, minimum, maximum, minimumInclusive,
                      maximumInclusive);
                }
              }
            }
          }
        } else if (c == '(') {
          // sub-query
          parser.next();
          return parseStandardQuery(parser, options, fieldName, depth + 1);
        }
      }
      while (parser.hasNext()) {
        c = parser.next();
        if ((c == ')') || (c == ' ') || (c == '"') || (c == '\'')) {
          parser.stepBack();
          break;
        }
      }
      int end = parser.getCurrentIndex();
      if (end > start) {
        String term = parser.substring(start, end);
        return createWordQuery(fieldName, term);
      }
    } catch (NlsRuntimeException e) {
      options.getErrorHandler().handleError(parser.getOriginalString(), start,
          parser.getCurrentIndex(), e);
    } catch (RuntimeException e) {
      SearchQueryParseException ex = new SearchQueryParseException(e);
      options.getErrorHandler().handleError(parser.getOriginalString(), start,
          parser.getCurrentIndex(), ex);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery parseStandardQuery(String query, SearchQueryBuilderOptions options) {

    CharSequenceScanner parser = new CharSequenceScanner(query);
    return parseStandardQuery(parser, options, SearchEntry.FIELD_TEXT, 0);
  }
}
