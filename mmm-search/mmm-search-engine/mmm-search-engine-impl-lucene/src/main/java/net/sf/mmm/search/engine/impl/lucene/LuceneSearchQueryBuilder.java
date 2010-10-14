/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.io.IOException;
import java.io.StringReader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchParseException;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.base.AbstractSearchQueryBuilder;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzer;
import net.sf.mmm.search.impl.lucene.LuceneAnalyzerImpl;
import net.sf.mmm.search.impl.lucene.LuceneVersion;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.engine.api.SearchQueryBuilder} interface using
 * lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class LuceneSearchQueryBuilder extends AbstractSearchQueryBuilder {

  /** @see #createNullQuery() */
  public static final LuceneSearchQuery NULL_QUERY = new LuceneSearchQuery(new MatchAllDocsQuery());

  /** the analyzer to use */
  private Analyzer analyzer;

  /** The {@link LuceneAnalyzer}. */
  private LuceneAnalyzer luceneAnalyzer;

  /** The {@link LuceneVersion}. */
  private LuceneVersion luceneVersion;

  /**
   * <code>true</code> if leading wildcards ('*' or '?') are ignored,
   * <code>false</code> otherwise.
   */
  private boolean ignoreLeadingWildcard;

  /**
   * The constructor.
   */
  public LuceneSearchQueryBuilder() {

    super();
  }

  /**
   * This method determines if leading wildcards will be ignored.<br>
   * <b>ATTENTION:</b><br>
   * Leading wildcards can cause performance problems. If you run a public
   * accessible search-engine and you want to avoid easy DOS-attacks, you should
   * disable leading wildcards.
   * 
   * @return <code>true</code> if leading wildcards ('*' or '?') are ignored,
   *         <code>false</code> otherwise.
   */
  public boolean isIgnoreLeadingWildcards() {

    return this.ignoreLeadingWildcard;
  }

  /**
   * This method sets the {@link #isIgnoreLeadingWildcards()
   * ignoreLeadingWildcard} flag. The default is <code>false</code>.
   * 
   * @param ignoreLeadingWildcard is the ignoreLeadingWildcard to set
   */
  public void setIgnoreLeadingWildcard(boolean ignoreLeadingWildcard) {

    getInitializationState().requireNotInitilized();
    this.ignoreLeadingWildcard = ignoreLeadingWildcard;
  }

  /**
   * @return the analyzer
   */
  protected Analyzer getAnalyzer() {

    return this.analyzer;
  }

  /**
   * @param analyzer is the analyzer to set
   */
  public void setAnalyzer(Analyzer analyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = analyzer;
  }

  /**
   * @param luceneAnalyzer is the luceneAnalyzer to set
   */
  @Inject
  public void setLuceneAnalyzer(LuceneAnalyzer luceneAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.luceneAnalyzer = luceneAnalyzer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.analyzer == null) {
      if (this.luceneAnalyzer == null) {
        LuceneAnalyzerImpl luceneAnalyzerImpl = new LuceneAnalyzerImpl();
        luceneAnalyzerImpl.initialize();
        this.luceneAnalyzer = luceneAnalyzerImpl;
      }
      this.analyzer = this.luceneAnalyzer.getAnalyzer();
    }
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
  public ComplexSearchQuery createComplexQuery() {

    return new LuceneComplexSearchQuery();
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery createPhraseQuery(String property, String phrase) {

    try {
      TokenStream tokenStream = this.analyzer.tokenStream(property, new StringReader(phrase));
      PhraseQuery luceneQuery = new PhraseQuery();
      TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
      while (tokenStream.incrementToken()) {
        luceneQuery.add(new Term(property, termAttribute.term()));
      }
      // tokenStream.end();
      // tokenStream.close();
      return new LuceneSearchQuery(luceneQuery);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery createTermQuery(String property, String term) {

    StringBuffer buffer = new StringBuffer(term.length());
    char[] chars = term.toCharArray();
    boolean hasPattern = false;
    boolean isPrefixQuery = true;
    int index = 0;
    while (index < chars.length) {
      char c = chars[index++];
      // skip duplicate '*'
      while ((c == '*') && (index < chars.length) && (chars[index] == '*')) {
        index++;
      }
      if ((c == '*') || (c == '?')) {
        if ((!this.ignoreLeadingWildcard) || (buffer.length() > 0)) {
          hasPattern = true;
          if (index < chars.length) {
            isPrefixQuery = false;
            buffer.append(c);
          } else if (!isPrefixQuery) {
            buffer.append(c);
          }
        }
      } else {
        buffer.append(Character.toLowerCase(c));
      }
    }
    String queryString = buffer.toString();
    Query luceneQuery;
    if (!hasPattern || isPrefixQuery) {
      try {
        TokenStream tokenStream = this.analyzer.tokenStream(property, new StringReader(term));
        TermAttribute termAttribute = tokenStream.getAttribute(TermAttribute.class);
        if (tokenStream.incrementToken()) {
          if (hasPattern) {
            luceneQuery = new PrefixQuery(new Term(property, termAttribute.term()));
          } else {
            luceneQuery = new TermQuery(new Term(property, termAttribute.term()));
          }
        } else {
          luceneQuery = new MatchAllDocsQuery();
        }
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.READ);
      }
    } else {
      luceneQuery = new WildcardQuery(new Term(property, queryString));
    }
    return new LuceneSearchQuery(luceneQuery);
  }

  /**
   * {@inheritDoc}
   */
  public SearchQuery parseNativeQuery(String query) throws SearchException {

    try {
      // according to javadoc the parser is NOT thread safe so we create an
      // instance per use...
      QueryParser parser = new QueryParser(this.luceneVersion.getLuceneVersion(),
          SearchEntry.PROPERTY_TEXT, this.analyzer);
      Query luceneQuery = parser.parse(query);
      return new LuceneSearchQuery(luceneQuery);
    } catch (ParseException e) {
      throw new SearchParseException(e, query);
    }
  }

}
