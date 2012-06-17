/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.List;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationLoader;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.test.TestResourceHelper;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SearchIndexerConfigurationLoader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchIndexerConfigurationLoaderTest {

  /**
   * This method gets the {@link SearchIndexerConfigurationLoader} to test.
   * 
   * @return the {@link SearchIndexerConfigurationLoader} to test.
   */
  protected SearchIndexerConfigurationLoader getConfigurationReader() {

    SearchIndexerConfigurationLoaderImpl impl = new SearchIndexerConfigurationLoaderImpl();
    impl.initialize();
    return impl;
  }

  /**
   * Tests {@link SearchIndexerConfigurationLoader#loadConfiguration(String)}.
   */
  @Test
  public void testRead() {

    SearchIndexerConfigurationLoader reader = getConfigurationReader();

    String resourceUri = TestResourceHelper.getTestPath(SearchIndexerConfigurationLoaderTest.class,
        ".xml");
    SearchIndexerConfigurationHolder configurationHolder = reader.loadConfiguration(resourceUri);
    SearchIndexerConfiguration configuration = configurationHolder.getBean();
    Assert.assertNotNull(configuration);
    reader.validateConfiguration(configuration);
    Assert.assertEquals("~/search-index", configuration.getSearchIndex().getLocation());

    // subversion source
    SearchIndexerSource sourceSvn = configuration.getSource("SVN");
    Assert.assertEquals("Subversion", sourceSvn.getTitle());
    List<? extends SearchIndexerDataLocation> locations = sourceSvn.getLocations();
    Assert.assertEquals(1, locations.size());
    SearchIndexerDataLocation location = locations.get(0);
    Assert.assertEquals("file:///data/repository", location.getLocationUri());
    Assert.assertEquals("SVN", location.getSource().getId());
    Assert.assertEquals("svn", location.getUpdateStrategyVariant());
    Assert.assertEquals("UTF-8", location.getEncoding());
    Filter<String> filter = location.getFilter();
    Assert.assertNotNull(filter);
    Assert.assertFalse(filter.accept("foo.XML"));
    Assert.assertFalse(filter.accept("a/b/c.xsl"));
    Assert.assertTrue(filter.accept("/data/c.xsl"));
    Assert.assertTrue(filter.accept("/doc/c.xsl"));
    Assert.assertFalse(filter.accept("/data/c.pdf"));

    // wiki source
    SearchIndexerSource sourceWiki = configuration.getSource("wiki");
    Assert.assertEquals("TWiki", sourceWiki.getTitle());
    Assert.assertEquals(0, sourceWiki.getLocations().size());
  }

  /**
   * Tests {@link SearchIndexerConfigurationLoader#loadConfiguration(String)}.
   */
  @Test
  public void testReadInvalidIds() {

    SearchIndexerConfigurationLoader reader = getConfigurationReader();

    String resourceUri = TestResourceHelper.getTestPath(SearchIndexerConfigurationLoaderTest.class,
        "-invalid-ids.xml");
    try {
      SearchIndexerConfigurationHolder configurationHolder = reader.loadConfiguration(resourceUri);
      configurationHolder.getBean();
      ExceptionHelper.failExceptionExpected();
    } catch (NlsRuntimeException e) {
      String message = e.getMessage();
      Assert.assertTrue(message.contains("my-filter"));
      Assert.assertTrue(message.contains("my-missing-filter"));
      Assert.assertTrue(message.contains("wrong-parent-id"));
    }
  }

}
