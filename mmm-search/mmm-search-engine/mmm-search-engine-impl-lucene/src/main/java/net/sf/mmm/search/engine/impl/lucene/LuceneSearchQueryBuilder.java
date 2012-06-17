/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.util.regex.Pattern;

import net.sf.mmm.search.api.config.SearchFieldConfiguration;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.base.AbstractSearchQueryBuilder;
import net.sf.mmm.util.lang.api.StringUtil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;

/**
 * This is the implementation of the {@link net.sf.mmm.search.engine.api.SearchQueryBuilder} interface using
 * lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchQueryBuilder extends AbstractSearchQueryBuilder {

  /**
   * The name of the {@link net.sf.mmm.search.api.config.SearchProperties#getProperty(String) property} for
   * {@link LuceneSearchQueryBuilder#isIgnoreLeadingWildcards()}.
   */
  public static final String PROPERTY_IGNORE_LEADING_WILDCARDS = "net.sf.mmm."
      + "search.engine.impl.lucene.LuceneSearchQueryBuilder.ignoreLeadingWildcards";

  /** The pattern to normalize wildcards. */
  private static final Pattern PATTERN_NORMALIZE_ASTERISK = Pattern.compile("([*][*?]+|[*?]+[*])");

  /** The pattern to normalize wildcards. */
  private static final Pattern PATTERN_NORMALIZE_QUESTIONMARK = Pattern.compile("[?]+");

  /** @see #createNullQuery() */
  public static final LuceneSearchQuery NULL_QUERY = new LuceneSearchQuery(new MatchAllDocsQuery());

  /** The analyzer to use. */
  private final Analyzer analyzer;

  /** The {@link Version} of lucene. */
  private final Version luceneVersion;

  /**
   * @see #isIgnoreLeadingWildcards()
   */
  private Boolean ignoreLeadingWildcard;

  /** The {@link LuceneFieldManager}. */
  private final LuceneFieldManager fieldManager;

  /**
   * The constructor.
   * 
   * @param analyzer is the {@link Analyzer}.
   * @param version is the {@link Version} of lucene.
   * @param fieldManager is the {@link LuceneFieldManager}.
   */
  public LuceneSearchQueryBuilder(Analyzer analyzer, Version version, LuceneFieldManager fieldManager) {

    super();
    this.analyzer = analyzer;
    this.luceneVersion = version;
    this.fieldManager = fieldManager;
  }

  /**
   * This method determines if leading wildcards will be ignored.<br>
   * <b>ATTENTION:</b><br>
   * Leading wildcards can cause performance problems. If you run a public accessible search-engine and you
   * want to avoid easy DOS-attacks, you should disable leading wildcards.
   * 
   * @return <code>true</code> if leading wildcards ('*' or '?') are ignored, <code>false</code> otherwise.
   */
  protected boolean isIgnoreLeadingWildcards() {

    Boolean result = this.ignoreLeadingWildcard;
    if (result == null) {
      boolean ignore;
      String wildcardProperty = this.fieldManager.getConfigurationHolder().getBean().getProperties()
          .getProperty(PROPERTY_IGNORE_LEADING_WILDCARDS);
      if (StringUtil.TRUE.equals(wildcardProperty)) {
        ignore = true;
      } else {
        if (!StringUtil.FALSE.equals(wildcardProperty)) {
          String message = "Leading wildcards are enabled in searches. "
              + "This can cause performance problems and may allow "
              + "denial-of-service-attacks. Please explicitly set the property '" + PROPERTY_IGNORE_LEADING_WILDCARDS
              + "' in your search-configuration to 'true' to disable or 'false' " + "to prevent this warning !";
          getLogger().warn(message);
        }
        ignore = false;
        this.ignoreLeadingWildcard = result;
      }
      result = Boolean.valueOf(ignore);
    }
    return result.booleanValue();
  }

  /**
   * @return the analyzer
   */
  protected Analyzer getAnalyzer() {

    return this.analyzer;
  }

  /**
   * This method gets the {@link Version}.
   * 
   * @return the {@link Version}.
   */
  protected Version getLuceneVersion() {

    return this.luceneVersion;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SearchQuery createNullQuery() {

    return NULL_QUERY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComplexSearchQuery createComplexQuery() {

    return new LuceneComplexSearchQuery();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchQuery createPhraseQuery(String field, String phrase) {

    Query luceneQuery = this.fieldManager.createPhraseQuery(field, phrase);
    return new LuceneSearchQuery(luceneQuery);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchQuery createRangeQuery(String field, String minimum, String maximum, boolean minimumInclusive,
      boolean maximumInclusive) {

    Query luceneQuery = this.fieldManager.createRangeQuery(field, minimum, maximum, minimumInclusive, maximumInclusive);
    return new LuceneSearchQuery(luceneQuery);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchQuery createWordQuery(String field, String word) {

    SearchFieldConfiguration fieldConfiguration = this.fieldManager.getConfigurationHolder().getBean().getFields()
        .getFieldConfiguration(field);
    boolean isString = true;
    if (fieldConfiguration != null) {
      isString = (fieldConfiguration.getType().getFieldClass() == String.class);
    }
    Query luceneQuery = null;
    if (isString) {
      String normalizedWord = word;
      normalizedWord = PATTERN_NORMALIZE_QUESTIONMARK.matcher(normalizedWord).replaceAll("?");
      normalizedWord = PATTERN_NORMALIZE_ASTERISK.matcher(normalizedWord).replaceAll("*");
      String prefixWord = normalizedWord;
      boolean prefix = false;
      boolean wildcard = false;
      if (prefixWord.endsWith("*")) {
        prefix = true;
        prefixWord = prefixWord.substring(0, prefixWord.length() - 1);
      }
      if (prefixWord.contains("*") || prefixWord.contains("?")) {
        wildcard = true;
        prefix = false;
      }
      if (wildcard && isIgnoreLeadingWildcards()) {
        if (normalizedWord.startsWith("*") || normalizedWord.startsWith("?")) {
          normalizedWord = normalizedWord.substring(1);
          if (!normalizedWord.contains("*") && !normalizedWord.contains("?")) {
            wildcard = false;
          }
        }
      }
      if (prefix) {
        normalizedWord = prefixWord;
      }
      if (prefix) {
        luceneQuery = new PrefixQuery(new Term(field, normalizedWord));
      } else if (wildcard) {
        luceneQuery = new WildcardQuery(new Term(field, normalizedWord));
      }
    }
    if (luceneQuery == null) {
      Term term = this.fieldManager.createTerm(field, word);
      luceneQuery = new TermQuery(term);
    }
    return new LuceneSearchQuery(luceneQuery);
  }

  // /**
  // * {@inheritDoc}
  // */
  // public SearchQuery parseNativeQuery(String query) throws SearchException {
  //
  // try {
  // // according to javadoc the parser is NOT thread safe so we create an
  // // instance per use...
  // QueryParser parser = new QueryParser(this.luceneVersion,
  // SearchEntry.FIELD_TEXT,
  // this.analyzer);
  // Query luceneQuery = parser.parse(query);
  // return new LuceneSearchQuery(luceneQuery);
  // } catch (ParseException e) {
  // throw new SearchQueryParseException(e, query);
  // }
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean refresh() {

    this.ignoreLeadingWildcard = null;
    return true;
  }

}
