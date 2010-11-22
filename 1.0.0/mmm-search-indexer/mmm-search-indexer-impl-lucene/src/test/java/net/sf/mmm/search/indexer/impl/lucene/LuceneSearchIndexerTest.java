/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.lucene;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.search.indexer.impl.SearchIndexerMain;
import net.sf.mmm.test.TestResourceHelper;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link LuceneSearchIndexer}. It is a complete
 * integration test that performs the actual indexing via
 * {@link SearchIndexerMain}. In order to test the result, it will use the
 * {@link SearchEngine}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchIndexerTest {

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
   * This method indexes the complete sources of this maven module. It will then
   * perform a search and check if it gets the correct results.
   */
  @Test
  public void testIndexer() {

    SearchIndexerMain main = new SearchIndexerMain();
    String configLocation = TestResourceHelper.getTestPath(LuceneSearchIndexerTest.class, ".xml");
    int exitCode = main.run(new String[] { "--config", configLocation, "--overwrite" });
    Assert.assertEquals(0, exitCode);
    SearchEngineConfigurationLoader configLoader = main.getIocContainer().getComponent(
        SearchEngineConfigurationLoader.class);
    SearchEngineConfigurationHolder configurationHolder = configLoader
        .loadConfiguration(configLocation);
    SearchEngineBuilder searchEngineBuilder = main.getIocContainer().getComponent(
        SearchEngineBuilder.class);
    String path = LuceneSearchIndexerTest.class.getName().replace('.', '/');
    ManagedSearchEngine searchEngine = searchEngineBuilder.createSearchEngine(configurationHolder);
    try {
      // this query contains a crazy word that is unique in this project, it
      // only occurs once: in this source-file (which has been previously
      // indexed).
      SearchHit hit;
      hit = getSingleHit(searchEngine, "Xulawolratzuwki");
      Assert.assertEquals(path + ".java", hit.getUri());
      Assert.assertEquals("Joerg Hohwiller", hit.getCreator());
      Assert.assertEquals("test-java", hit.getSource());
      hit = getSingleHit(searchEngine, "androgynous +source:test-resources");
      Assert.assertEquals(path + ".xml", hit.getUri());
    } finally {
      searchEngine.close();
    }
  }
}
