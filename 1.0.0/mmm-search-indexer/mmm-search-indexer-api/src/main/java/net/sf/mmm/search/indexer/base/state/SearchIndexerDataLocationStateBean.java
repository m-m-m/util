/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.state.SearchIndexerDataLocationState;

/**
 * This is the implementation of {@link SearchIndexerDataLocationState} as
 * JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "location-state")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexerDataLocationStateBean implements SearchIndexerDataLocationState {

  /** @see #getLocation() */
  @XmlAttribute(name = "location")
  private String location;

  /** @see #getRevision() */
  @XmlAttribute(name = "revision")
  private String revision;

  /**
   * The constructor.
   */
  public SearchIndexerDataLocationStateBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getLocation() {

    return this.location;
  }

  /**
   * @param location is the location to set
   */
  public void setLocation(String location) {

    this.location = location;
  }

  /**
   * {@inheritDoc}
   */
  public String getRevision() {

    return this.revision;
  }

  /**
   * {@inheritDoc}
   */
  public void setRevision(String revision) {

    this.revision = revision;
  }

}
