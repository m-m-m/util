/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.search.base.config.SearchIndexConfigurationBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.state.SearchIndexerDataLocationState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateManager;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationBean;
import net.sf.mmm.search.indexer.base.config.SearchIndexerDataLocationBean;
import net.sf.mmm.search.indexer.base.config.SearchIndexerSourceBean;
import net.sf.mmm.test.TestResourceHelper;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.file.base.FileUtilImpl;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link SearchIndexerStateManagerImpl}.
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

  /** A test location: {@value} . */
  protected static final String LOCATION_XVCS = "xvcs://hyper/repository";

  /**
   * @return the {@link SearchIndexerStateManager} to test.
   */
  protected SearchIndexerStateManager getSearchIndexerStateManager() {

    SearchIndexerStateManagerImpl stateManager = new SearchIndexerStateManagerImpl();
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
    List<SearchIndexerSourceBean> sources = new ArrayList<SearchIndexerSourceBean>();
    List<SearchIndexerDataLocationBean> locations;
    SearchIndexerSourceBean sourceBean;

    // bar
    sourceBean = new SearchIndexerSourceBean();
    sourceBean.setId(SOURCE_BAR);
    sources.add(sourceBean);
    locations = new ArrayList<SearchIndexerDataLocationBean>();
    SearchIndexerDataLocationBean locationBean = new SearchIndexerDataLocationBean();
    locationBean.setLocaltionUri(LOCATION_DATA);
    locations.add(locationBean);
    sourceBean.setLocations(locations);
    // foo
    sourceBean = new SearchIndexerSourceBean();
    sourceBean.setId(SOURCE_FOO);
    sources.add(sourceBean);
    indexerConfiguration.setSources(sources);
    locations = new ArrayList<SearchIndexerDataLocationBean>();
    locationBean = new SearchIndexerDataLocationBean();
    locationBean.setLocaltionUri(LOCATION_MUSIC);
    locations.add(locationBean);
    sourceBean.setLocations(locations);

    return indexerConfiguration;
  }

  /**
   * This method checks the {@link SearchIndexerState} if created by default
   * from empty state-configuration according to
   * {@link #createIndexerConfiguration(String)} .
   * 
   * @param state is the {@link SearchIndexerState} to check.
   */
  private void checkEmptyState(SearchIndexerState state) {

    SearchIndexerDataLocationState locationState;
    SearchIndexerSourceState sourceState;
    // check empty sources and locations are created
    // bar source
    sourceState = state.getSourceState(SOURCE_BAR);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_BAR, sourceState.getSource());
    Assert.assertNull(sourceState.getIndexingDate());
    // locations for bar
    locationState = sourceState.getLocationState(LOCATION_DATA);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_DATA, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());

    // foo source
    sourceState = state.getSourceState(SOURCE_FOO);
    Assert.assertNotNull(sourceState);
    Assert.assertEquals(SOURCE_FOO, sourceState.getSource());
    Assert.assertNull(sourceState.getIndexingDate());
    // locations for foo
    locationState = sourceState.getLocationState(LOCATION_MUSIC);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_MUSIC, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());

    Assert.assertNull(state.getSourceState("not-existing-source"));

    // check empty locations are created
    Assert.assertNull(sourceState.getLocationState("not-existing-location"));
  }

  /**
   * Tests {@link SearchIndexerStateManager#load(SearchIndexerConfiguration)}
   */
  @Test
  public void testLoadNotExists() {

    SearchIndexerConfiguration indexerConfiguration = createIndexerConfiguration("file://target/non-existent-folder");

    SearchIndexerStateManager stateManager = getSearchIndexerStateManager();
    SearchIndexerState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);
    checkEmptyState(state);
  }

  /**
   * Tests {@link SearchIndexerStateManager#load(SearchIndexerConfiguration)}
   */
  @Test
  public void testLoadExists() {

    String location = TestResourceHelper.getTestPath(
        SearchIndexStateManagerTest.class.getPackage(), "");
    SearchIndexerConfiguration indexerConfiguration = createIndexerConfiguration(location);

    SearchIndexerStateManager stateManager = getSearchIndexerStateManager();
    SearchIndexerState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);

    // check empty sources are created
    SearchIndexerSourceState sourceStateBar = state.getSourceState(SOURCE_BAR);
    Assert.assertNotNull(sourceStateBar);
    Assert.assertEquals(SOURCE_BAR, sourceStateBar.getSource());
    Assert.assertNull(sourceStateBar.getIndexingDate());
    SearchIndexerSourceState sourceStateFoo = state.getSourceState(SOURCE_FOO);
    Assert.assertNotNull(sourceStateFoo);
    Assert.assertEquals(SOURCE_FOO, sourceStateFoo.getSource());
    Assert.assertEquals(Iso8601UtilImpl.getInstance().parseDate("2010-01-31T23:59:58Z"),
        sourceStateFoo.getIndexingDate());
    SearchIndexerSourceState sourceStateSome = state.getSourceState("some");
    Assert.assertNotNull(sourceStateSome);
    Assert.assertEquals("some", sourceStateSome.getSource());
    Assert.assertEquals(Iso8601UtilImpl.getInstance().parseDate("1999-12-22T12:23:45Z"),
        sourceStateSome.getIndexingDate());
    Assert.assertNull(state.getSourceState("not-existing-source"));

    // check empty locations are created
    SearchIndexerDataLocationState locationState;
    locationState = sourceStateBar.getLocationState(LOCATION_DATA);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_DATA, locationState.getLocation());
    Assert.assertNull(locationState.getRevision());
    locationState = sourceStateFoo.getLocationState(LOCATION_MUSIC);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_MUSIC, locationState.getLocation());
    Assert.assertEquals("rev42", locationState.getRevision());
    locationState = sourceStateSome.getLocationState(LOCATION_XVCS);
    Assert.assertNotNull(locationState);
    Assert.assertEquals(LOCATION_XVCS, locationState.getLocation());
    Assert.assertEquals("{base-5fe9ad}", locationState.getRevision());
    Assert.assertNull(sourceStateSome.getLocationState("not-existing-location"));
  }

  /**
   * Tests {@link SearchIndexerStateManager#save(SearchIndexerState)}.
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

    SearchIndexerStateManager stateManager = getSearchIndexerStateManager();
    SearchIndexerState state = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state);
    checkEmptyState(state);

    // modify state

    Date fooDate = new Date(123456789L);
    SearchIndexerSourceState sourceStateFoo = state.getSourceState(SOURCE_FOO);
    sourceStateFoo.setIndexingDate(fooDate);
    Date barDate = new Date(987654321L);
    SearchIndexerSourceState sourceStateBar = state.getSourceState(SOURCE_BAR);
    sourceStateBar.setIndexingDate(barDate);
    String dataRevision = "42.42";
    sourceStateBar.getLocationState(LOCATION_DATA).setRevision(dataRevision);
    // leave music revision null

    // save state
    stateManager.save(state);

    // load state again...
    SearchIndexerState state2 = stateManager.load(indexerConfiguration);
    Assert.assertNotNull(state2);
    Assert.assertNotSame(state, state2);
    SearchIndexerSourceState sourceStateFoo2 = state2.getSourceState(SOURCE_FOO);
    Assert.assertEquals(fooDate, sourceStateFoo2.getIndexingDate());
    SearchIndexerSourceState sourceStateBar2 = state2.getSourceState(SOURCE_BAR);
    Assert.assertEquals(barDate, sourceStateBar2.getIndexingDate());
    Assert
        .assertEquals(dataRevision, sourceStateBar2.getLocationState(LOCATION_DATA).getRevision());
    Assert.assertNull(sourceStateFoo2.getLocationState(LOCATION_MUSIC).getRevision());
  }
}
