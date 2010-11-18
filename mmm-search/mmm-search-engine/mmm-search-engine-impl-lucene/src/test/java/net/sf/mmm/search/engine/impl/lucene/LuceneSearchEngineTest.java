/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.impl.lucene;

import java.util.Iterator;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.search.engine.base.config.SearchEngineConfigurationLoaderImpl;
import net.sf.mmm.test.TestResourceHelper;

import org.junit.Test;

/**
 * This is the test-case for the {@link LuceneSearchEngine}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class LuceneSearchEngineTest {

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
   * This method creates a {@link ManagedSearchEngine} from a test-configuration
   * file that points to a search-index dedicated for this test (initially
   * created with the search-indexer). The search-index contains a historic
   * revision of the sources of <code>mmm-util-core</code>.
   */
  @Test
  public void testSearch() {

    String locationUrl = TestResourceHelper.getTestPath(LuceneSearchEngineTest.class, ".xml");
    locationUrl = SearchConfiguration.DEFAULT_CONFIGURATION_URL;
    SearchEngineConfigurationHolder configurationHolder = getSearchEngineConfigurationLoader()
        .loadConfiguration(locationUrl);
    ManagedSearchEngine searchEngine = getSearchEngineBuilder().createSearchEngine(
        configurationHolder);
    SearchQuery query = searchEngine.getQueryBuilder().parseStandardQuery("id:3194511877816188929");
    SearchResultPage page = searchEngine.search(query, 50);
    System.out.println(page.getTotalHitCount());
    for (int i = 0; i < page.getPageHitCount(); i++) {
      SearchHit hit = page.getPageHit(i);
      Iterator<String> fieldNameIt = hit.getFieldNames();
      while (fieldNameIt.hasNext()) {
        String field = fieldNameIt.next();
        System.out.println(field + ":" + hit.getFieldAsString(field));
      }
      System.out.println(hit.getHighlightedText());
      System.out.println();
    }
  }
}
