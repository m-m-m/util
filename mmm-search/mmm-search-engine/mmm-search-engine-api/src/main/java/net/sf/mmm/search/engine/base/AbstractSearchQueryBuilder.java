/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.util.StringParser;
import net.sf.mmm.util.filter.CharFilter;

/**
 * This is the abstract base implementation of the {@link SearchQueryBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchQueryBuilder implements SearchQueryBuilder {

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

    return createTermQuery(SearchEntry.PROPERTY_TEXT, "*");
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery parseStandardQuery(String query) throws SearchException {
  
    return parseStandardQuery(query, false);
  }
  
  /**
   * @see #parseStandardQuery(String, boolean)
   * 
   * @param parser
   * @param requireTerms
   * @param defaultProperty
   * @param depth
   *        is the deepth of the query expression (number of open parenthesis).
   * @return the parsed query or <code>null</code> if this a call with a depth
   *         greater than <code>0</code> and the parsed query segment was
   *         void.
   */
  private SearchQuery parseStandardQuery(StringParser parser, boolean requireTerms,
      String defaultProperty, int depth) {

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
      subQuery = parseStandardClause(parser, defaultProperty, depth);
      if (subQuery != null) {
        if (conjunction == '+') {
          complexQuery.addRequiredQuery(subQuery);
        } else if (conjunction == '-') {
          complexQuery.addExcludingQuery(subQuery);
        } else if (requireTerms && (depth == 0)) {
          complexQuery.addRequiredQuery(subQuery);
        } else {
          complexQuery.addOptionalQuery(subQuery);
        }
      }
      if (parser.hasNext()) {
        if (parser.peek() == ')') {
          if (depth > 0) {
            parser.next();
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
   * @param parser
   * @param defaultProperty
   * @param depth
   *        is the deepth of the query expression (number of open parenthesis).
   * @return the parsed query or <code>null</code> if the parsed query segment
   *         was void.
   */
  private SearchQuery parseStandardClause(StringParser parser, String defaultProperty, int depth) {

    char c;
    String property = defaultProperty;
    int start = parser.getCurrentIndex();
    String ascii = parser.readWhile(CharFilter.ASCII_LETTER_FILTER);
    if (ascii.length() > 0) {
      c = parser.forceNext();
      if (c == ':') {
        property = ascii;
        start = parser.getCurrentIndex();
      } else {
        // lookahead was negative, step back...
        parser.setCurrentIndex(start);
      }
    }
    if (parser.hasNext()) {
      c = parser.peek();
      if ((c == '"') || (c == '\'')) {
        parser.next();
        String phrase = parser.readUntil(c, true);
        return createPhraseQuery(property, phrase);
      } else if (c == '(') {
        parser.next();
        return parseStandardQuery(parser, false, property, depth + 1);
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
      return createTermQuery(property, term);
    } else {
      return null;      
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery parseStandardQuery(String query, boolean requireTerms) {

    StringParser parser = new StringParser(query);
    return parseStandardQuery(parser, requireTerms, SearchEntry.PROPERTY_TEXT, 0);
  }
}
