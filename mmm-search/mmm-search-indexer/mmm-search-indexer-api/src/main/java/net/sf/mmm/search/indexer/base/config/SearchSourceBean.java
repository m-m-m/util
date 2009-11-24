/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import net.sf.mmm.search.indexer.api.config.SearchSource;

/**
 * This is the implementation of the {@link SearchSource} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchSourceBean implements SearchSource {

  /** @see #getId() */
  @XmlAttribute(name = "id")
  private String id;

  /** @see #getTitle() */
  @XmlAttribute(name = "title")
  private String title;

  /** @see #getUrlPrefix() */
  @XmlAttribute(name = "url-prefix")
  private String urlPrefix;

  /**
   * The constructor.
   */
  public SearchSourceBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  public String getUrlPrefix() {

    return this.urlPrefix;
  }

  /**
   * @param id is the id to set
   */
  public void setId(String id) {

    this.id = id;
  }

  /**
   * @param title is the title to set
   */
  public void setTitle(String title) {

    this.title = title;
  }

  /**
   * @param urlPrefix is the urlPrefix to set
   */
  public void setUrlPrefix(String urlPrefix) {

    this.urlPrefix = urlPrefix;
  }

}
