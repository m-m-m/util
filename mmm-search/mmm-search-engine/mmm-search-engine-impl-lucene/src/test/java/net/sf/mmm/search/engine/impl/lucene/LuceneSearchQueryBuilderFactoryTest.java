/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.util.List;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchQueryBuilderFactory;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.base.config.SearchEngineConfigurationBean;
import net.sf.mmm.search.engine.base.config.SearchEngineConfigurationHolderImpl;
import net.sf.mmm.util.nls.api.NlsClassCastException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link LuceneSearchQueryBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchQueryBuilderFactoryTest {

  /**
   * This method gets the {@link SearchQueryBuilderFactory} to test.
   * 
   * @return the {@link SearchQueryBuilderFactory}.
   */
  protected SearchQueryBuilderFactory getQueryBuilderFactory() {

    LuceneSearchQueryBuilderFactory factory = new LuceneSearchQueryBuilderFactory();
    factory.initialize();
    return factory;
  }

  /**
   * This method gets the {@link SearchQueryBuilder} to test.
   * 
   * @return the {@link SearchQueryBuilder}.
   */
  protected SearchQueryBuilder getQueryBuilder() {

    SearchQueryBuilderFactory factory = getQueryBuilderFactory();
    SearchEngineConfigurationBean configuration = new SearchEngineConfigurationBean();
    configuration.getProperties().setProperty(LuceneSearchQueryBuilder.PROPERTY_IGNORE_LEADING_WILDCARDS, "false");
    SearchEngineConfigurationHolder holder = new SearchEngineConfigurationHolderImpl(configuration);
    return factory.createQueryBuilder(holder);
  }

  /**
   * This method gets the underlying lucene {@link Query} from the given <code>query</code>.
   * 
   * @param query is the {@link SearchQuery}.
   * @return the wrapped {@link Query}.
   */
  protected Query getLuceneQuery(SearchQuery query) {

    return getLuceneQuery(query, Query.class);
  }

  /**
   * This method gets the underlying lucene {@link Query} from the given <code>query</code>.
   * 
   * @param <Q> is the generic type of the <code>queryClass</code>.
   * 
   * @param query is the {@link SearchQuery}.
   * @param queryClass is the {@link Class} reflecting the expected type of the {@link Query}.
   * @return the wrapped {@link Query}.
   */
  protected <Q extends Query> Q getLuceneQuery(SearchQuery query, Class<Q> queryClass) {

    Assert.assertNotNull(SearchQuery.class.getSimpleName() + " is null!", query);
    Assert.assertTrue(
        SearchQuery.class.getSimpleName() + " is no instance of "
            + AbstractLuceneSearchQuery.class.getSimpleName() + "!", query instanceof AbstractLuceneSearchQuery);
    Query luceneQuery = ((AbstractLuceneSearchQuery) query).getLuceneQuery();
    return getLuceneQuery(luceneQuery, queryClass);
  }

  /**
   * This method performs a checked cast of the given lucene <code>query</code> to the type specified by
   * <code>queryClass</code>.
   * 
   * @param <Q> is the generic type of the <code>queryClass</code>.
   * 
   * @param query is the {@link Query}.
   * @param queryClass is the {@link Class} reflecting the expected type of the {@link Query}.
   * @return the casted {@link Query}.
   */
  protected <Q extends Query> Q getLuceneQuery(Query query, Class<Q> queryClass) {

    Assert.assertNotNull("lucene query is null!", query);
    Assert.assertTrue("Query (" + query + ") has type " + query.getClass().getSimpleName() + " - expected "
        + queryClass.getSimpleName(), queryClass.isAssignableFrom(queryClass));
    if (!queryClass.isAssignableFrom(query.getClass())) {
      throw new NlsClassCastException(query, queryClass);
    }
    return queryClass.cast(query);
  }

  /**
   * Test of {@link SearchQueryBuilder#createPhraseQuery(String, String)} .
   */
  @Test
  public void testCreatePhraseQuery() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    String[] termStrings = new String[] { "yo", "check", "thiz", "out" };
    String phrase = null;
    for (String term : termStrings) {
      if (phrase == null) {
        phrase = term;
      } else {
        phrase = phrase + " " + term;
      }
    }
    SearchQuery query = queryBuilder.createPhraseQuery(SearchEntry.FIELD_TEXT, phrase);
    PhraseQuery phraseQuery = getLuceneQuery(query, PhraseQuery.class);
    Term[] terms = phraseQuery.getTerms();
    Assert.assertNotNull(terms);
    Assert.assertEquals(termStrings.length, terms.length);
    for (int i = 0; i < termStrings.length; i++) {
      Assert.assertEquals(termStrings[i], terms[i].text());
      Assert.assertEquals(SearchEntry.FIELD_TEXT, terms[i].field());
    }
  }

  /**
   * Test of {@link SearchQueryBuilder#createWordQuery(String, String)} for wildcard query.
   */
  @Test
  public void testCreateTermQueryWildcardPrefix() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    SearchQuery query = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "*foo");

    WildcardQuery luceneQuery = getLuceneQuery(query, WildcardQuery.class);
    Term term = luceneQuery.getTerm();
    Assert.assertNotNull(term);
    Assert.assertEquals("*foo", term.text());
    Assert.assertEquals(SearchEntry.FIELD_TEXT, term.field());
  }

  /**
   * Test of {@link SearchQueryBuilder#createWordQuery(String, String)} for wildcard query.
   */
  @Test
  public void testCreateTermQueryWildcardMixed() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    SearchQuery query = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "bar*foo?quux");

    WildcardQuery luceneQuery = getLuceneQuery(query, WildcardQuery.class);
    Term term = luceneQuery.getTerm();
    Assert.assertNotNull(term);
    Assert.assertEquals("bar*foo?quux", term.text());
    Assert.assertEquals(SearchEntry.FIELD_TEXT, term.field());
  }

  /**
   * Test of {@link SearchQueryBuilder#createWordQuery(String, String)} for prefix query.
   */
  @Test
  public void testCreateTermQueryPrefix() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    SearchQuery query = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "foo*");
    PrefixQuery luceneQuery = getLuceneQuery(query, PrefixQuery.class);
    Term term = luceneQuery.getPrefix();
    Assert.assertNotNull(term);
    Assert.assertEquals("foo", term.text());
    Assert.assertEquals(SearchEntry.FIELD_TEXT, term.field());
  }

  /**
   * Test of {@link SearchQueryBuilder#createWordQuery(String, String)} for term query.
   */
  @Test
  public void testCreateTermQueryTerm() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    SearchQuery query = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "foo");

    TermQuery luceneQuery = getLuceneQuery(query, TermQuery.class);
    Term term = luceneQuery.getTerm();
    Assert.assertNotNull(term);
    Assert.assertEquals("foo", term.text());
    Assert.assertEquals(SearchEntry.FIELD_TEXT, term.field());
  }

  /**
   * Test of {@link SearchQueryBuilder#createComplexQuery()}.
   */
  @Test
  public void testCreateComplexQuery() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    ComplexSearchQuery complexQuery = queryBuilder.createComplexQuery();
    BooleanQuery booleanQuery = getLuceneQuery(complexQuery, BooleanQuery.class);
    Assert.assertEquals(0, complexQuery.getSubQueryCount());

    SearchQuery subQuery1 = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "foo");
    complexQuery.addRequiredQuery(subQuery1);
    Assert.assertEquals(1, complexQuery.getSubQueryCount());
    SearchQuery subQuery2 = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "bar");
    complexQuery.addRequiredQuery(subQuery2);
    Assert.assertEquals(2, complexQuery.getSubQueryCount());
    SearchQuery subQuery3 = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "some");
    complexQuery.addExcludingQuery(subQuery3);
    Assert.assertEquals(3, complexQuery.getSubQueryCount());
    SearchQuery subQuery4 = queryBuilder.createWordQuery(SearchEntry.FIELD_TEXT, "thing");
    complexQuery.addOptionalQuery(subQuery4);
    Assert.assertEquals(4, complexQuery.getSubQueryCount());
    List<BooleanClause> clauses = booleanQuery.clauses();
    Assert.assertNotNull(clauses);
    Assert.assertEquals(4, clauses.size());
    Assert.assertSame(getLuceneQuery(subQuery1), clauses.get(0).getQuery());
    Assert.assertSame(Occur.MUST, clauses.get(0).getOccur());
    Assert.assertSame(getLuceneQuery(subQuery2), clauses.get(1).getQuery());
    Assert.assertSame(Occur.MUST, clauses.get(1).getOccur());
    Assert.assertSame(getLuceneQuery(subQuery3), clauses.get(2).getQuery());
    Assert.assertSame(Occur.MUST_NOT, clauses.get(2).getOccur());
    Assert.assertSame(getLuceneQuery(subQuery4), clauses.get(3).getQuery());
    Assert.assertSame(Occur.SHOULD, clauses.get(3).getOccur());
  }

  /**
   * Test of {@link SearchQueryBuilder#parseStandardQuery(String)}.
   */
  @Test
  public void testParseStandardQuery() {

    SearchQueryBuilder queryBuilder = getQueryBuilder();
    SearchQuery query = queryBuilder.parseStandardQuery("foo *bar* +(some* thing) -exclude");
    BooleanQuery booleanQuery = getLuceneQuery(query, BooleanQuery.class);
    List<BooleanClause> clauses = booleanQuery.clauses();
    Assert.assertNotNull(clauses);
    Assert.assertEquals(4, clauses.size());
    Assert.assertEquals("foo", getLuceneQuery(clauses.get(0).getQuery(), TermQuery.class).getTerm().text());
    Assert.assertSame(Occur.SHOULD, clauses.get(0).getOccur());
    Assert.assertEquals("*bar*", getLuceneQuery(clauses.get(1).getQuery(), WildcardQuery.class).getTerm().text());
    Assert.assertSame(Occur.SHOULD, clauses.get(1).getOccur());
    Assert.assertEquals("exclude", getLuceneQuery(clauses.get(3).getQuery(), TermQuery.class).getTerm().text());
    Assert.assertSame(Occur.MUST_NOT, clauses.get(3).getOccur());
    BooleanQuery subQuery = getLuceneQuery(clauses.get(2).getQuery(), BooleanQuery.class);
    Assert.assertSame(Occur.MUST, clauses.get(2).getOccur());
    List<BooleanClause> subClauses = subQuery.clauses();
    Assert.assertNotNull(subClauses);
    Assert.assertEquals(2, subClauses.size());
    Assert
        .assertEquals("some", getLuceneQuery(subClauses.get(0).getQuery(), PrefixQuery.class).getPrefix().text());
    Assert.assertSame(Occur.SHOULD, subClauses.get(0).getOccur());
    Assert.assertEquals("thing", getLuceneQuery(subClauses.get(1).getQuery(), TermQuery.class).getTerm().text());
    Assert.assertSame(Occur.SHOULD, subClauses.get(1).getOccur());

    query = queryBuilder.parseStandardQuery("()");
    MatchAllDocsQuery allDocsQuery = getLuceneQuery(query, MatchAllDocsQuery.class);
    Assert.assertNotNull(allDocsQuery);
  }

}
