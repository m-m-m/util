/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.state.SearchIndexDataLocationState;
import net.sf.mmm.search.indexer.api.state.SearchIndexSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexState;

/**
 * This is the implementation of {@link SearchIndexState} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search-index-state")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexStateBean implements SearchIndexState {

  /** @see #getIndexingDate() */
  @XmlElement(name = "indexing-date")
  private Date indexingDate;

  /** @see #getSources() */
  @XmlElementWrapper(name = "sources")
  @XmlElement(name = "source-state")
  private List<SearchIndexSourceStateBean> sources;

  /** @see #getSourceStateMap() */
  private transient Map<String, SearchIndexSourceStateBean> sourceStateMap;

  /** @see #getLocations() */
  @XmlElementWrapper(name = "locations")
  @XmlElement(name = "location-state")
  private List<SearchIndexDataLocationStateBean> locations;

  /** @see #getLocationStateMap() */
  private transient Map<String, SearchIndexDataLocationStateBean> locationStateMap;

  /** @see #getConfigurationLocation() */
  private transient String configurationLocation;

  /**
   * The constructor.
   */
  public SearchIndexStateBean() {

    super();
  }

  /**
   * @return the sourceMap
   */
  protected Map<String, SearchIndexSourceStateBean> getSourceStateMap() {

    if (this.sourceStateMap == null) {
      Map<String, SearchIndexSourceStateBean> map = new HashMap<String, SearchIndexSourceStateBean>();
      if (this.sources != null) {
        for (SearchIndexSourceStateBean source : this.sources) {
          map.put(source.getSource(), source);
        }
      }
      this.sourceStateMap = map;
    }
    return this.sourceStateMap;
  }

  /**
   * This method gets the {@link List} of {@link SearchIndexSourceStateBean}.<br/>
   * <b>ATTENTION:</b><br/>
   * Do NOT modify this list externally.
   * 
   * @see #getOrCreateSourceState(String)
   * 
   * @return the {@link List} of {@link SearchIndexSourceStateBean}s.
   */
  public List<SearchIndexSourceStateBean> getSources() {

    return this.sources;
  }

  /**
   * @param sources is the sources to set
   */
  public void setSources(List<SearchIndexSourceStateBean> sources) {

    this.sources = sources;
    this.sources = null;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexSourceState getSourceState(String source) {

    return getSourceStateMap().get(source);
  }

  /**
   * @see #getSourceState(String)
   * 
   * @param source is the source-ID for which the state is requested.
   * @return the requested {@link SearchIndexSourceState}. If it does not exist,
   *         it is created and associated.
   */
  public SearchIndexSourceState getOrCreateSourceState(String source) {

    SearchIndexSourceStateBean sourceStatus = getSourceStateMap().get(source);
    if (sourceStatus == null) {
      sourceStatus = new SearchIndexSourceStateBean();
      sourceStatus.setSource(source);
      if (this.sources == null) {
        this.sources = new ArrayList<SearchIndexSourceStateBean>();
      }
      this.sources.add(sourceStatus);
      getSourceStateMap().put(source, sourceStatus);
    }
    return sourceStatus;
  }

  /**
   * This method gets the {@link List} of
   * {@link SearchIndexDataLocationStateBean}.<br/>
   * <b>ATTENTION:</b><br>
   * Do NOT modify this list externally.
   * 
   * @see #getOrCreateLocationState(String)
   * 
   * @return the locations
   */
  public List<SearchIndexDataLocationStateBean> getLocations() {

    return this.locations;
  }

  /**
   * @param locations is the locations to set
   */
  public void setLocations(List<SearchIndexDataLocationStateBean> locations) {

    this.locations = locations;
    this.locationStateMap = null;
  }

  /**
   * @return the locationStateMap
   */
  protected Map<String, SearchIndexDataLocationStateBean> getLocationStateMap() {

    if (this.locationStateMap == null) {
      Map<String, SearchIndexDataLocationStateBean> map = new HashMap<String, SearchIndexDataLocationStateBean>();
      if (this.locations != null) {
        for (SearchIndexDataLocationStateBean location : this.locations) {
          map.put(location.getLocation(), location);
        }
      }
      this.locationStateMap = map;
    }
    return this.locationStateMap;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexDataLocationState getLocationState(String location) {

    return getLocationStateMap().get(location);
  }

  /**
   * @see #getLocationState(String)
   * 
   * @param location is the location for which the state is requested.
   * @return the requested {@link SearchIndexDataLocationState}. If it does not
   *         exist, it is created and associated.
   */
  public SearchIndexDataLocationState getOrCreateLocationState(String location) {

    SearchIndexDataLocationStateBean locationStatus = getLocationStateMap().get(location);
    if (locationStatus == null) {
      locationStatus = new SearchIndexDataLocationStateBean();
      locationStatus.setLocation(location);
      if (this.locations == null) {
        this.locations = new ArrayList<SearchIndexDataLocationStateBean>();
      }
      this.locations.add(locationStatus);
      getLocationStateMap().put(location, locationStatus);
    }
    return locationStatus;
  }

  /**
   * This method gets the location URI where the configuration for this bean is
   * stored. This is a transient attribute dynamically added after loading.
   * 
   * @return the configurationLocation
   */
  public String getConfigurationLocation() {

    return this.configurationLocation;
  }

  /**
   * @param configurationLocation is the location to set
   */
  public void setConfigurationLocation(String configurationLocation) {

    this.configurationLocation = configurationLocation;
  }

  /**
   * {@inheritDoc}
   */
  public Date getIndexingDate() {

    return this.indexingDate;
  }

  /**
   * This method sets the {@link #getIndexingDate() indexing-date}.
   * 
   * @param indexingDate is the new indexing-date.
   */
  public void setIndexingDate(Date indexingDate) {

    this.indexingDate = indexingDate;
  }

}
