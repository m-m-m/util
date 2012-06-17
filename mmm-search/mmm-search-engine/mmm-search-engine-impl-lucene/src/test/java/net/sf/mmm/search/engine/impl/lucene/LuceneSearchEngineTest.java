/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.search.engine.base.config.SearchEngineConfigurationLoaderImpl;
import net.sf.mmm.test.TestResourceHelper;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the {@link LuceneSearchEngine}.<br/>
 * It uses a search-index that was created once by indexing the sources of
 * mmm-util-core.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchEngineTest {

  /** The size of the source-code of StringUtil. */
  private static final Long STRING_UTIL_SIZE = Long.valueOf(14058);

  /** The ID of the source-code of StringUtil. */
  private static final Long STRING_UTIL_ID = Long.valueOf(3201666874941112579L);

  /** The title of the source-code of StringUtil. */
  private static final String STRING_UTIL_TITLE = "net.sf.mmm.util.lang.api.StringUtil";

  /** The URI of the source-code of StringUtil. */
  private static final String STRING_UTIL_URI = "net/sf/mmm/util/lang/api/StringUtil.java";

  /** The source-id. */
  private static final String SOURCE_ID = "mmm-util-core";

  /**
   * This method gets the {@link SearchEngineBuilder} to test.
   * 
   * @return the {@link SearchEngineBuilder} to test.
   */
  protected SearchEngineBuilder getSearchEngineBuilder() {

    LuceneSearchEngineBuilder searchEngineBuilder = new LuceneSearchEngineBuilder();
    searchEngineBuilder.initialize();
    return searchEngineBuilder;
  }

  /**
   * This method gets the {@link SearchEngineConfigurationLoader} to test.
   * 
   * @return the {@link SearchEngineConfigurationLoader} to test.
   */
  protected SearchEngineConfigurationLoader getSearchEngineConfigurationLoader() {

    SearchEngineConfigurationLoaderImpl reader = new SearchEngineConfigurationLoaderImpl();
    reader.initialize();
    return reader;
  }

  /**
   * This method performs a search for the given <code>query</code> that is
   * expected to return a single {@link SearchHit} as result.
   * 
   * @param searchEngine is the {@link SearchEngine}.
   * @param query is the {@link SearchQuery}.
   * @return the {@link SearchHit}.
   */
  protected SearchHit getSingleHit(SearchEngine searchEngine, String query) {

    SearchQuery searchQuery = searchEngine.getQueryBuilder().parseStandardQuery(query);
    SearchResultPage page = searchEngine.search(searchQuery, 10);
    Assert.assertNotNull(page);
    Assert.assertEquals(1, page.getPageHitCount());
    return page.getPageHit(0);
  }

  /**
   * This method checks that the given <code>entry</code> is the source-code of
   * StringUtil at the time it was indexed for this test.
   * 
   * @param entry is the {@link SearchEntry}.
   */
  protected void checkStringUtil(SearchEntry entry) {

    Assert.assertEquals(STRING_UTIL_URI, entry.getUri());
    Assert.assertEquals(SOURCE_ID, entry.getSource());
    Assert.assertEquals(STRING_UTIL_SIZE, entry.getSize());
    Assert.assertEquals(STRING_UTIL_TITLE, entry.getTitle());
    Assert.assertEquals("java", entry.getType());
    Assert.assertEquals(STRING_UTIL_ID, entry.getId());
  }

  /**
   * This method creates a {@link ManagedSearchEngine} from a test-configuration
   * file that points to a search-index dedicated for this test (initially
   * created with the search-indexer). The search-index contains a historic
   * revision of the sources of <code>mmm-util-core</code>.
   */
  @Test
  public void testSearch() {

    String locationUrl = TestResourceHelper.getTestPath(LuceneSearchEngineTest.class, ".xml");
    SearchEngineConfigurationHolder configurationHolder = getSearchEngineConfigurationLoader()
        .loadConfiguration(locationUrl);
    ManagedSearchEngine searchEngine = getSearchEngineBuilder().createSearchEngine(
        configurationHolder);
    Assert.assertEquals(744, searchEngine.getTotalEntryCount());
    Assert.assertEquals(744, searchEngine.count("source", SOURCE_ID));
    Assert.assertEquals(744, searchEngine.count("type", "java"));
    Assert.assertEquals(0, searchEngine.count("source", "undefined"));

    SearchHit hit;
    hit = getSingleHit(searchEngine, "uri:" + STRING_UTIL_URI);
    Assert.assertEquals(5, hit.getScore(5));
    checkStringUtil(hit);
    hit = getSingleHit(searchEngine, "title:" + STRING_UTIL_TITLE);
    Assert.assertEquals(5, hit.getScore(5));
    checkStringUtil(hit);
    hit = getSingleHit(searchEngine, "id:" + STRING_UTIL_ID);
    Assert.assertEquals(5, hit.getScore(5));
    checkStringUtil(hit);
    hit = getSingleHit(searchEngine, "size:" + STRING_UTIL_SIZE);
    Assert.assertEquals(5, hit.getScore(5));
    checkStringUtil(hit);
    hit = getSingleHit(searchEngine, "size:{13924 - 14524}");
    Assert.assertEquals(5, hit.getScore(5));
    checkStringUtil(hit);
    SearchQuery query = searchEngine.getQueryBuilder().parseStandardQuery("size:[13924 - 14524]");
    SearchResultPage page = searchEngine.search(query, 20);
    Assert.assertEquals(3, page.getPageHitCount());
    Set<String> uriSet = new HashSet<String>();
    for (SearchHit searchHit : page) {
      uriSet.add(searchHit.getUri());
    }
    Assert.assertTrue(uriSet.contains(STRING_UTIL_URI));
    Assert.assertTrue(uriSet
        .contains("net/sf/mmm/util/pojo/descriptor/impl/DefaultPojoDescriptorEnhancer.java"));
    Assert.assertTrue(uriSet.contains("net/sf/mmm/util/reflect/api/CollectionReflectionUtil.java"));
  }
}
