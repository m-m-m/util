/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.search.base.config.SearchIndexConfigurationBean;
import net.sf.mmm.search.base.config.SearchSourceBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.state.SearchIndexDataLocationState;
import net.sf.mmm.search.indexer.api.state.SearchIndexSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexState;
import net.sf.mmm.search.indexer.api.state.SearchIndexStateManager;
import net.sf.mmm.search.indexer.base.config.SearchIndexDataLocationBean;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationBean;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.file.base.FileUtilImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SearchIndexStateManagerImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexStateManagerTest {

  /** A test source: {@value} . */
  protected static final String SOURCE_FOO = "foo";

  /** A test source: {@value} . */
  protected static final String SOURCE_BAR = "bar";

  /** A test location: {@value} . */
  protected static final String LOCATION_DATA = "file:///data";

  /** A test location: {@value} . */
  protected static final String LOCATION_MUSIC = "file://~/music";

  /**
   * @return the {@link SearchIndexStateManager} to test.
   */
  protected SearchIndexStateManager getSearchIndexStateManager() {

    SearchIndexStateManagerImpl stateManager = new SearchIndexStateManagerImpl();
    stateManager.initialize();
    return stateManager;
  }

  /**
   * This method creates some test {@link SearchIndexerConfiguration}.
   * 
   * @param location is the {@link SearchIndexConfigurationBean#getLocation()
   *        index-location}.
   * @return the created {@link SearchIndexerConfiguration}.
   */
  protected SearchIndexerConfiguration createIndexerConfiguration(String location) {

    SearchIndexerConfigurationBean indexerConfiguration = new SearchIndexerConfigurationBean();
    SearchIndexConfigurationBean searchIndex = new SearchIndexConfigurationBean();
    searchIndex.setLocation(location);
    indexerConfiguration.setSearchIndex(searchIndex);
    List<SearchSourceBean> sources = new ArrayList<SearchSourceBean>();
    SearchSourceBean sourceBean = new SearchSourceBean();
    sourceBean.setId(SOURCE_BAR);
    sources.add(sourceBean);
    sourceBean = new SearchSourceBean();
    sourceBean.setId(SOURCE_FOO);
    sources.add(sourceBean);
    indexerConfiguration.setSources(sources);
    List<SearchIndexDataLocationBean> locations = new ArrayList<SearchIndexDataLocationBean>();
    SearchIndexDataLocationBean locationBean = new SearchIndexDataLocationBean();
    locationBean.setLocaltion(LOCATION_DATA);
    locations.add(locationBean);
    locationBean = new SearchIndexDataLocationBean();
    locationBean.setLocaltion(LOCATION_MUSIC);
    locations.add(locationBean);
    indexerConfiguration.setLocations(locations);
    return indexerConfiguration;
  }

  /**
   * This method checks the {@link SearchIndexState} if created by default from
   * empty state-configuration according to
   * {@link #createIndexerConfiguration(String)} .
   * 
   * @param state is the {@link SearchIndexState} to check.
   */
  private void checkEmptyState(SearchIndexState state) {

    // check empty sources are created
    SearchIndexSourceState sourceState = state.getSourceState(SOURCE_BAR);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_BAR, sourceState.getSource());
    Assert.assertNull(sourceState.getIndexingDate());
    sourceState = state.getSourceState(SOURCE_FOO);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_FOO, sourceState.getSource());
    Assert.assertNull(sourceState.getIndexingDate());
    Assert.assertNull(state.getSourceState("not-existing-source"));

    // check empty locations are created
    SearchIndexDataLocationState locationState = state.getLocationState(LOCATION_DATA);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_DATA, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());
    locationState = state.getLocationState(LOCATION_MUSIC);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_MUSIC, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());
    Assert.assertNull(state.getLocationState("not-existing-location"));
  }

  /**
   * Tests {@link SearchIndexStateManager#load(SearchIndexerConfiguration)}
   */
  @Test
  public void testLoadNotExists() {

    SearchIndexerConfiguration indexerConfiguration = createIndexerConfiguration("file://target/non-existent-folder");

    SearchIndexStateManager stateManager = getSearchIndexStateManager();
    SearchIndexState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);
    checkEmptyState(state);
  }

  /**
   * Tests {@link SearchIndexStateManager#load(SearchIndexerConfiguration)}
   */
  @Test
  public void testLoadExists() {

    SearchIndexerConfiguration indexerConfiguration = createIndexerConfiguration("file://src/test/resources/net/sf/mmm/search/indexer/state01");

    SearchIndexStateManager stateManager = getSearchIndexStateManager();
    SearchIndexState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);

    // check empty sources are created
    SearchIndexSourceState sourceState = state.getSourceState(SOURCE_BAR);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_BAR, sourceState.getSource());
    Assert.assertNull(sourceState.getIndexingDate());
    sourceState = state.getSourceState(SOURCE_FOO);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_FOO, sourceState.getSource());
    Assert.assertEquals(Iso8601UtilImpl.getInstance().parseDate("2010-01-31T23:59:58Z"),
        sourceState.getIndexingDate());
    sourceState = state.getSourceState("some");
    Assert.assertNotNull(sourceState);
    Assert.assertEquals("some", sourceState.getSource());
    Assert.assertEquals(Iso8601UtilImpl.getInstance().parseDate("1999-12-22T12:23:45Z"),
        sourceState.getIndexingDate());
    Assert.assertNull(state.getSourceState("not-existing-source"));

    // check empty locations are created
    SearchIndexDataLocationState locationState = state.getLocationState(LOCATION_DATA);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_DATA, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());
    locationState = state.getLocationState(LOCATION_MUSIC);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_MUSIC, locationState.getLocation());
    Assert.assertEquals("rev42", locationState.getRevision());
    String magicLocation = "xvcs://hyper/repository";
    locationState = state.getLocationState(magicLocation);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(magicLocation, locationState.getLocation());
    Assert.assertEquals("{base-5fe9ad}", locationState.getRevision());
    Assert.assertNull(state.getLocationState("not-existing-location"));
  }

  /**
   * Tests {@link SearchIndexStateManager#save(SearchIndexState)}.
   */
  @Test
  public void testSave() {

    String indexDirectory = "target/test-index";
    File file = new File(indexDirectory);
    // ensure the directory does not exist from previous test runs
    FileUtilImpl.getInstance().deleteRecursive(file);
    // create new, empty directory...
    Assert.assertTrue(file.mkdirs());
    SearchIndexerConfiguration indexerConfiguration = createIndexerConfiguration("file://"
        + indexDirectory);

    // load not existent state

    SearchIndexStateManager stateManager = getSearchIndexStateManager();
    SearchIndexState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);
    checkEmptyState(state);

    // modify state

    Date fooDate = new Date(123456789L);
    state.getSourceState(SOURCE_FOO).setIndexingDate(fooDate);
    Date barDate = new Date(987654321L);
    state.getSourceState(SOURCE_BAR).setIndexingDate(barDate);
    String dataRevision = "42.42";
    state.getLocationState(LOCATION_DATA).setRevision(dataRevision);
    // leave music revision null

    // save state

    stateManager.save(state);

    // load state again...
    SearchIndexState state2 = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state2);
    Assert.assertNotSame(state, state2);
    Assert.assertEquals(fooDate, state2.getSourceState(SOURCE_FOO).getIndexingDate());
    Assert.assertEquals(barDate, state2.getSourceState(SOURCE_BAR).getIndexingDate());
    Assert.assertEquals(dataRevision, state2.getLocationState(LOCATION_DATA).getRevision());
    Assert.assertNull(state2.getLocationState(LOCATION_MUSIC).getRevision());

  }
}
