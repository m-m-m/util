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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.state.SearchIndexerDataLocationState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;

/**
 * This is the implementation of {@link SearchIndexerSourceState} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "source-state")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexerSourceStateBean implements SearchIndexerSourceState {

  /** @see #getSource() */
  @XmlAttribute(name = "source")
  private String source;

  /** @see #getIndexingDate() */
  @XmlElement(name = "indexing-date")
  private Date indexingDate;

  /** @see #getLocations() */
  @XmlElementWrapper(name = "locations")
  @XmlElement(name = "location-state")
  private List<SearchIndexerDataLocationStateBean> locations;

  /** @see #getLocationStateMap() */
  private transient Map<String, SearchIndexerDataLocationStateBean> locationStateMap;

  /**
   * The constructor.
   */
  public SearchIndexerSourceStateBean() {

    super();
  }

  /**
   * @return the source
   */
  public String getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  public void setSource(String source) {

    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  public Date getIndexingDate() {

    return this.indexingDate;
  }

  /**
   * {@inheritDoc}
   */
  public void setIndexingDate(Date indexingDate) {

    this.indexingDate = indexingDate;
  }

  /**
   * This method gets the {@link List} of
   * {@link SearchIndexerDataLocationStateBean}.<br/>
   * <b>ATTENTION:</b><br>
   * Do NOT modify this list externally.
   * 
   * @see #getOrCreateLocationState(String)
   * 
   * @return the locations
   */
  public List<SearchIndexerDataLocationStateBean> getLocations() {

    return this.locations;
  }

  /**
   * @param locations is the locations to set
   */
  public void setLocations(List<SearchIndexerDataLocationStateBean> locations) {

    this.locations = locations;
    this.locationStateMap = null;
  }

  /**
   * @return the locationStateMap
   */
  protected Map<String, SearchIndexerDataLocationStateBean> getLocationStateMap() {

    if (this.locationStateMap == null) {
      Map<String, SearchIndexerDataLocationStateBean> map = new HashMap<String, SearchIndexerDataLocationStateBean>();
      if (this.locations != null) {
        for (SearchIndexerDataLocationStateBean location : this.locations) {
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
  public SearchIndexerDataLocationState getLocationState(String location) {

    return getLocationStateMap().get(location);
  }

  /**
   * @see #getLocationState(String)
   * 
   * @param location is the location for which the state is requested.
   * @return the requested {@link SearchIndexerDataLocationState}. If it does
   *         not exist, it is created and associated.
   */
  public SearchIndexerDataLocationState getOrCreateLocationState(String location) {

    SearchIndexerDataLocationStateBean locationStatus = getLocationStateMap().get(location);
    if (locationStatus == null) {
      locationStatus = new SearchIndexerDataLocationStateBean();
      locationStatus.setLocation(location);
      if (this.locations == null) {
        this.locations = new ArrayList<SearchIndexerDataLocationStateBean>();
      }
      this.locations.add(locationStatus);
      getLocationStateMap().put(location, locationStatus);
    }
    return locationStatus;
  }

}
