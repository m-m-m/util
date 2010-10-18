/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.state.SearchIndexSourceState;

/**
 * This is the implementation of {@link SearchIndexSourceState} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "source-state")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexSourceStateBean implements SearchIndexSourceState {

  /** @see #getSource() */
  @XmlAttribute(name = "source")
  private String source;

  /** @see #getIndexingDate() */
  @XmlElement(name = "indexing-date")
  private Date indexingDate;

  /**
   * The constructor.
   */
  public SearchIndexSourceStateBean() {

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

}
