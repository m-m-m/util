/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.api.SearchException;

/**
 * This is the interface used to build {@link SearchQuery search-queries}.<br>
 * You can either specify the query as string and
 * {@link #parseStandardQuery(String, boolean) parse} it or
 * {@link #createComplexQuery() create} your query constructively.<br>
 * Here an example:<br>
 * 
 * <pre>
 * {@link SearchQueryBuilder} queryBuilder = mySearchEngine.{@link SearchEngine#getQueryBuilder() getQueryBuilder()};
 * {@link ComplexSearchQuery} query = queryBuilder.{@link #createComplexQuery()};
 * {@link SearchQuery} subQuery1 = queryBuilder.{@link #createTermQuery(String, 
 * String) createTermQuery}({@link net.sf.mmm.search.api.SearchEntry#PROPERTY_TEXT 
 * SearchEntry.PROPERTY_TEXT}, "Multimedia*");
 * query.{@link ComplexSearchQuery#addRequiredQuery(SearchQuery) addRequiredQuery}(subQuery1);
 * {@link SearchQuery} subQuery2 = queryBuilder.{@link #createTermQuery(String, 
 * String) createTermQuery}({@link net.sf.mmm.search.api.SearchEntry#PROPERTY_TEXT 
 * SearchEntry.PROPERTY_TEXT}, "PHP");
 * query.{@link ComplexSearchQuery#addExcludingQuery(SearchQuery) addExcludingQuery}(subQuery2);
 * {@link SearchQuery} subQuery3 = queryBuilder.{@link #createPhraseQuery(String, 
 * String) createPhraseQuery}({@link net.sf.mmm.search.api.SearchEntry#PROPERTY_TITLE 
 * SearchEntry.PROPERTY_TITLE}, "Enterprise Content Management");
 * query.{@link ComplexSearchQuery#addOptionalQuery(SearchQuery) addOptionalQuery}(subQuery3);
 * </pre>
 * 
 * The resulting <code>query</code> will be the same as if
 * {@link #parseStandardQuery(String)} was called with "+text:Multimedia*
 * -text:PHP title:\"Enterprise Content Management\"".
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchQueryBuilder {

  /**
   * This method parses the given <code>query</code> string in the native query
   * language of the underlying implementation. This allows to use
   * power-features that are not available otherwise via this API. For an
   * implementation independent query language use
   * {@link #parseStandardQuery(String, boolean)} instead.
   * 
   * @param query is the query to parse as string.
   * @return the parsed query.
   * @throws SearchException if the given <code>query</code> string is illegal
   *         and can NOT be parsed. Implementations should be tolerant and try
   *         to avoid this situation.
   */
  SearchQuery parseNativeQuery(String query) throws SearchException;

  /**
   * This method parses the given <code>query</code> string in the standard
   * query language of this specification.<br>
   * The standard query language is defined by the following EBNF grammar:
   * 
   * <pre>
   * &lt;WHITESPACE> = (' '|'\t'|'\n'|'\r')
   * &lt;WHITESPACES> = (&lt;WHITESPACE>)+
   * &lt;START_CHAR> = ^(&lt;WHITESPACE>|'+'|'-'|'('|')'|'"'|'\'')
   * &lt;CHAR> = (&lt;START_CHAR>|'+'|'-')
   * &lt;TERM> = &lt;START_CHAR> (&lt;CHAR>)*
   * &lt;PHRASE> = '"' (^('"'))* '"'
   * &lt;MATCH> = (&lt;PHRASE> | &lt;TERM>)
   * &lt;PROPERTY> = ('a'-'z'|'A'-'Z')+
   * &lt;CLAUSE> = ['+'|'-'] [&lt;PROPERTY> ':'] ( &lt;MATCH> | '(' &lt;QUERY> ')' )
   * &lt;QUERY> = &lt;CLAUSE> | &lt;CLAUSE> (&lt;WHITESPACES> &lt;QUERY>)* ) 
   * </pre>
   * 
   * @see #parseNativeQuery(String)
   * 
   * @param query is the query to parse as string.
   * @return the parsed query.
   * @throws SearchException if the given <code>query</code> string is illegal
   *         and can NOT be parsed. Implementations should be tolerant and try
   *         to avoid this situation.
   */
  SearchQuery parseStandardQuery(String query) throws SearchException;

  /**
   * This method parses the given <code>query</code> string in the standard
   * query language of this specification.<br>
   * The standard query language is defined by the following EBNF grammar:
   * 
   * <pre>
   * &lt;WHITESPACE> = (' '|'\t'|'\n'|'\r')
   * &lt;WHITESPACES> = (&lt;WHITESPACE>)+
   * &lt;START_CHAR> = ^(&lt;WHITESPACE>|'+'|'-'|'('|')'|'"'|'\'')
   * &lt;CHAR> = (&lt;START_CHAR>|'+'|'-')
   * &lt;TERM> = &lt;START_CHAR> (&lt;CHAR>)*
   * &lt;PHRASE> = '"' (^('"'))* '"'
   * &lt;MATCH> = (&lt;PHRASE> | &lt;TERM>)
   * &lt;PROPERTY> = ('a'-'z'|'A'-'Z')+
   * &lt;CLAUSE> = ['+'|'-'] [&lt;PROPERTY> ':'] ( &lt;MATCH> | '(' &lt;QUERY> ')' )
   * &lt;QUERY> = &lt;CLAUSE> | &lt;CLAUSE> (&lt;WHITESPACES> &lt;QUERY>)* ) 
   * </pre>
   * 
   * @see #parseNativeQuery(String)
   * 
   * @param query is the query to parse as string.
   * @param requireTerms - if <code>true</code> regular terms of the query are
   *        required ("foo bar" -> "+foo +bar"), <code>false</code> otherwise
   *        ("foo bar" -> "foo OR bar"). In other words the call with
   *        <code>query</code> and <code>true</code> is the same as the call
   *        with <code>"+(" + query + ")"</code> and <code>false</code>.
   * @return the parsed query.
   * @throws SearchException if the given <code>query</code> string is illegal
   *         and can NOT be parsed. Implementations should be tolerant and try
   *         to avoid this situation.
   */
  SearchQuery parseStandardQuery(String query, boolean requireTerms) throws SearchException;

  /**
   * This method creates a new {@link SearchQuery query} for the given
   * <code>phrase</code> and <code>property</code>.
   * 
   * @param property is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getProperty(String)
   *        property} where to search.
   * @param phrase is the exact phrase to search for.
   * @return the created query.
   */
  SearchQuery createPhraseQuery(String property, String phrase);

  /**
   * This method creates a new {@link SearchQuery query} for the given
   * <code>term</code> and <code>property</code>.
   * 
   * @param property is the name of the
   *        {@link net.sf.mmm.search.api.SearchEntry#getProperty(String)
   *        property} where to search.
   * @param term is the single term to search for. This is eigther a single word
   *        or or a glob pattern (e.g. "moon?i*" to match "moonlight" or
   *        "moonride") to match.
   * 
   * @return the created query.
   */
  SearchQuery createTermQuery(String property, String term);

  /**
   * This method creates a {@link ComplexSearchQuery complex query}. You can
   * then add sub-queries to this query with logical operations.
   * 
   * @return the created query.
   */
  ComplexSearchQuery createComplexQuery();

}
